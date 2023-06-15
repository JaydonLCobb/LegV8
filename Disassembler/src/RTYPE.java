public class RTYPE {
    int i;
    int j;
    int k;
    int l;
    public RTYPE(int i, int j, int k, int l){
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
    }
    public static RTYPE setRType(byte[] arr, int index){
        int a = Byte.toUnsignedInt(arr[index + 1]);
        int b = Byte.toUnsignedInt(arr[index + 2]);
        int c = Byte.toUnsignedInt(arr[index + 3]);

        int RM = a & 0x7;
        int SH = (b >> 2) & 0x3F;
        int RN = ((b & 0x3) << 8) | c;
        int RD = (b >> 4) & 0xF;

        return new RTYPE(RM, SH, RN, RD);
    }
}
