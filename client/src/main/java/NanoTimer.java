import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!di")
public final class NanoTimer extends Timer {

	@OriginalMember(owner = "client!di", name = "i", descriptor = "J")
	private long targetTime = 0L;

	@OriginalMember(owner = "client!di", name = "k", descriptor = "J")
	private long currentTimeWithDrift = 0L;

	@OriginalMember(owner = "client!di", name = "l", descriptor = "J")
	private long lastTimeWithDrift = 0L;

	@OriginalMember(owner = "client!di", name = "<init>", descriptor = "()V")
	public NanoTimer() {
		this.currentTimeWithDrift = System.nanoTime();
		this.targetTime = System.nanoTime();
	}

	@OriginalMember(owner = "client!di", name = "a", descriptor = "(BJ)I")
	@Override
	protected int getTickCount(@OriginalArg(1) long frequency) {
		if (this.targetTime > this.currentTimeWithDrift) {
			this.lastTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
			this.currentTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
			this.targetTime += frequency;
			return 1;
		}

		@Pc(12) int count = 0;
		do {
			this.targetTime += frequency;
			count++;
		} while (count < 10 && this.currentTimeWithDrift > this.targetTime);
		if (this.targetTime < this.currentTimeWithDrift) {
			this.targetTime = this.currentTimeWithDrift;
		}
		return count;
	}

	@OriginalMember(owner = "client!di", name = "b", descriptor = "(B)J")
	@Override
	protected long getSleepTime() {
		this.currentTimeWithDrift += this.calculateDrift();
		return this.currentTimeWithDrift < this.targetTime ? (this.targetTime - this.currentTimeWithDrift) / 1_000_000L : 0L;
	}

	@OriginalMember(owner = "client!di", name = "b", descriptor = "(I)J")
	@Override
	public long getCurrentTimeWithDrift() {
		return this.currentTimeWithDrift;
	}

	@OriginalMember(owner = "client!di", name = "c", descriptor = "(B)J")
	private long calculateDrift() {
		@Pc(1) long now = System.nanoTime();
		@Pc(7) long delta = now - this.lastTimeWithDrift;
		this.lastTimeWithDrift = now;
		return delta;
	}

	@OriginalMember(owner = "client!di", name = "a", descriptor = "(I)V")
	@Override
	public void reset() {
		if (this.targetTime > this.currentTimeWithDrift) {
			this.currentTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
		}
		this.lastTimeWithDrift = 0L;
	}
}
