import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

public final class Static269 {

	@OriginalMember(owner = "client!iha", name = "c", descriptor = "Lclient!sb;")
	public static Js5 aJs558;

	@OriginalMember(owner = "client!iha", name = "a", descriptor = "(ILjava/lang/String;IZ)I")
	public static int parseInt(@OriginalArg(1) String input, @OriginalArg(2) int radix) {
		if (radix > 36) {
			throw new IllegalArgumentException("Invalid radix:" + radix);
		}
		@Pc(29) boolean isNegative = false;
		@Pc(31) boolean isValid = false;
		@Pc(39) int result = 0;
		@Pc(42) int digits = input.length();
		for (@Pc(44) int i = 0; i < digits; i++) {
			@Pc(49) char c = input.charAt(i);
			if (i == 0) {
				if (c == '-') {
					isNegative = true;
					continue;
				}
				if (c == '+') {
					continue;
				}
			}
			@Pc(104) int digit;
			if (c >= '0' && c <= '9') {
				digit = c - '0';
			} else if (c >= 'A' && c <= 'Z') {
				digit = c - '7';
			} else if (c >= 'a' && c <= 'z') {
				digit = c - 'W';
			} else {
				throw new NumberFormatException();
			}
			if (digit >= radix) {
				throw new NumberFormatException();
			}
			if (isNegative) {
				digit = -digit;
			}
			@Pc(136) int nextResult = digit + radix * result;
			if (result != nextResult / radix) {
				throw new NumberFormatException();
			}
			isValid = true;
			result = nextResult;
		}
		if (!isValid) {
			throw new NumberFormatException();
		}
		return result;
	}

	@OriginalMember(owner = "client!iha", name = "a", descriptor = "(Z)V")
	public static void method3909() {
		Static480.aLruHashTable2.clear();
	}

	@OriginalMember(owner = "client!iha", name = "a", descriptor = "(IIIIIIBII)V")
	public static void method3911(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(7) int arg5, @OriginalArg(8) int arg6) {
		Static384.aClass75Array2[Static317.anInt5046++] = new Class75(4, arg5, arg1, arg6, arg6, arg1, arg2, arg4, arg4, arg2, arg3, arg3, arg0, arg0);
	}
}
