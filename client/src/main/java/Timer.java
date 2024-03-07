import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

@OriginalClass("client!nl")
public abstract class Timer {

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "(BJ)I")
	protected abstract int getTickCount(@OriginalArg(1) long arg0);

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "(I)V")
	public abstract void reset();

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "(JI)I")
	public final int getTimerTicks(@OriginalArg(0) long frequency) {
		@Pc(13) long sleepTimeMs = this.getSleepTime();
		if (sleepTimeMs > 0L) {
			Static638.sleep(sleepTimeMs);
		}
		return this.getTickCount(frequency);
	}

	@OriginalMember(owner = "client!nl", name = "b", descriptor = "(B)J")
	protected abstract long getSleepTime();

	@OriginalMember(owner = "client!nl", name = "b", descriptor = "(I)J")
	public abstract long getCurrentTimeWithDrift();
}
