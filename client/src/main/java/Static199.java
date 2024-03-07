import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public final class Static199 {

	@OriginalMember(owner = "client!gd", name = "g", descriptor = "(I)V")
	public static void method2977() {
		Static600.aTimer1.reset();
		for (@Pc(8) int local8 = 0; local8 < 32; local8++) {
			Static475.aLongArray16[local8] = 0L;
		}
		for (@Pc(19) int local19 = 0; local19 < 32; local19++) {
			Static221.timeStamps[local19] = 0L;
		}
		Static32.frameCountLeft = 0;
	}
}
