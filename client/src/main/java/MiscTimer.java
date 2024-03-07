import jagex3.jagmisc.jagmisc;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!mp")
public final class MiscTimer extends Timer {

	@OriginalMember(owner = "client!mp", name = "l", descriptor = "J")
	private long targetTime;

	@OriginalMember(owner = "client!mp", name = "g", descriptor = "J")
	private long currentTimeWithDrift;

	@OriginalMember(owner = "client!mp", name = "j", descriptor = "J")
	private long lastTimeWithDrift = 0L;

	@OriginalMember(owner = "client!mp", name = "<init>", descriptor = "()V")
	public MiscTimer() {
		this.targetTime = this.currentTimeWithDrift = jagmisc.nanoTime();
		if (this.currentTimeWithDrift == 0L) {
			throw new RuntimeException();
		}
	}

	@OriginalMember(owner = "client!mp", name = "a", descriptor = "(I)V")
	@Override
	public void reset() {
		this.lastTimeWithDrift = 0L;
		if (this.currentTimeWithDrift < this.targetTime) {
			this.currentTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
		}
	}

	@OriginalMember(owner = "client!mp", name = "d", descriptor = "(I)J")
	private long calculateDrift() {
		@Pc(1) long now = jagmisc.nanoTime();
		@Pc(14) long delta = now - this.lastTimeWithDrift;
		this.lastTimeWithDrift = now;
		return delta;
	}

	@OriginalMember(owner = "client!mp", name = "b", descriptor = "(I)J")
	@Override
	public long getCurrentTimeWithDrift() {
		return this.currentTimeWithDrift;
	}

	@OriginalMember(owner = "client!mp", name = "b", descriptor = "(B)J")
	@Override
	protected long getSleepTime() {
		this.currentTimeWithDrift += this.calculateDrift();
		return this.targetTime > this.currentTimeWithDrift ? (this.targetTime - this.currentTimeWithDrift) / 1000000L : 0L;
	}

	@OriginalMember(owner = "client!mp", name = "a", descriptor = "(BJ)I")
	@Override
	protected int getTickCount(@OriginalArg(1) long frequency) {
		if (this.targetTime > this.currentTimeWithDrift) {
			this.lastTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
			this.currentTimeWithDrift += this.targetTime - this.currentTimeWithDrift;
			this.targetTime += frequency;
			return 1;
		}
		@Pc(46) int count = 0;
		do {
			this.targetTime += frequency;
			count++;
		} while (count < 10 && this.currentTimeWithDrift > this.targetTime);
		if (this.currentTimeWithDrift > this.targetTime) {
			this.targetTime = this.currentTimeWithDrift;
		}
		return count;
	}
}
