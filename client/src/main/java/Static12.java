import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public final class Static12 {

	@OriginalMember(owner = "client!ah", name = "i", descriptor = "Lclient!st;")
	public static Class23 aClass23_26;

	@OriginalMember(owner = "client!ah", name = "e", descriptor = "Lclient!st;")
	public static Class23 aClass23_27;

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "I")
	public static int anInt5741;

	@OriginalMember(owner = "client!ah", name = "h", descriptor = "Lclient!fca;")
	public static Class123 aClass123_4;

	@OriginalMember(owner = "client!ah", name = "f", descriptor = "Lclient!hc;")
	public static final Class155 aClass155_24 = new Class155(13);

	@OriginalMember(owner = "client!ah", name = "d", descriptor = "Lclient!lga;")
	public static final Class225 aClass225_147 = new Class225(51, 0);

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "(Ljava/lang/String;IZ)I")
	public static int method5162(@OriginalArg(0) String arg0) {
		return Static269.method3908(arg0, 16);
	}

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "(BII[B)I")
	public static int method5163(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) byte[] arg2) {
		@Pc(11) int local11 = -1;
		for (@Pc(13) int local13 = arg1; local13 < arg0; local13++) {
			local11 = local11 >>> 8 ^ Class388.anIntArray819[(local11 ^ arg2[local13]) & 0xFF];
		}
		return ~local11;
	}

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "(Lclient!ge;I)V")
	public static void method5164(@OriginalArg(0) Class2_Sub21 arg0) {
		while (true) {
			@Pc(20) int local20 = arg0.method7396();
			if (local20 == 0) {
				Static482.anInt7228 = arg0.method7382();
				Static134.anInt10330 = arg0.method7382();
			} else if (local20 == 255) {
				return;
			}
		}
	}
}