import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!au")
public final class MillisTimer extends Timer {

	@OriginalMember(owner = "client!au", name = "j", descriptor = "J")
	private long currentTimeWithDrift = 0L;

	@OriginalMember(owner = "client!au", name = "h", descriptor = "I")
	private int lastDriftsIndex = 0;

	@OriginalMember(owner = "client!au", name = "k", descriptor = "[J")
	private final long[] lastDrifts = new long[10];

	@OriginalMember(owner = "client!au", name = "g", descriptor = "I")
	private int driftsRecorded = 1;

	@OriginalMember(owner = "client!au", name = "i", descriptor = "J")
	private long targetTime = 0L;

	@OriginalMember(owner = "client!au", name = "l", descriptor = "J")
	private long lastTimeWithDrift = 0L;

	@OriginalMember(owner = "client!au", name = "a", descriptor = "(I)V")
	@Override
	public void reset() {
		this.lastTimeWithDrift = 0L;
		if (this.targetTime > this.currentTimeWithDrift) {
			this.currentTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
		}
	}

	@OriginalMember(owner = "client!au", name = "b", descriptor = "(B)J")
	@Override
	protected long getSleepTime() {
		this.currentTimeWithDrift += this.calculateDrift();
		return this.currentTimeWithDrift < this.targetTime ? (this.targetTime - this.currentTimeWithDrift) / 1_000_000L : 0L;
	}

	@OriginalMember(owner = "client!au", name = "b", descriptor = "(I)J")
	@Override
	public long getCurrentTimeWithDrift() {
		return this.currentTimeWithDrift;
	}

	@OriginalMember(owner = "client!au", name = "a", descriptor = "(BJ)I")
	@Override
	protected int getTickCount(@OriginalArg(1) long frequency) {
		if (this.targetTime > this.currentTimeWithDrift) {
			this.lastTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
			this.currentTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
			this.targetTime += frequency;
			return 1;
		}
		@Pc(47) int count = 0;
		do {
			count++;
			this.targetTime += frequency;
		} while (count < 10 && this.currentTimeWithDrift > this.targetTime);
		if (this.currentTimeWithDrift > this.targetTime) {
			this.targetTime = this.currentTimeWithDrift;
		}
		return count;
	}

	@OriginalMember(owner = "client!au", name = "c", descriptor = "(B)J")
	private long calculateDrift() {
		@Pc(10) long now = Static588.getCurrentTimeWithDrift() * 1_000_000L;
		@Pc(16) long delta = now - this.lastTimeWithDrift;
		this.lastTimeWithDrift = now;
		if (delta > -5_000_000_000L && delta < 5_000_000_000L) {
			this.lastDrifts[this.lastDriftsIndex] = delta;
			this.lastDriftsIndex = (this.lastDriftsIndex + 1) % 10;
			if (this.driftsRecorded < 10) {
				this.driftsRecorded++;
			}
		}
		@Pc(61) long totalDrift = 0L;
		for (@Pc(63) int i = 1; i <= this.driftsRecorded; i++) {
			totalDrift += this.lastDrifts[(this.lastDriftsIndex + 10 - i) % 10];
		}
		return totalDrift / (long) this.driftsRecorded;
	}
}
