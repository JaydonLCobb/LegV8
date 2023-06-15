public class ITYPE {
    int i;
    int l;
    int k;
    public ITYPE(int i, int k, int l){
        this.i = i;
        this.k = k;
        this.l = l;
    }

    public static ITYPE setIType(byte[] arr, int index){
        int a = Byte.toUnsignedInt(arr[index + 1]);
        int b = Byte.toUnsignedInt(arr[index + 2]);

        int ALU = ((a << 4) & 0xF00) | (b & 0xFF);
        int RN = ((b >> 4) & 0xF) | ((arr[index + 3] & 0xC0) << 2);
        int RT = arr[index + 3] & 0x7;

        return new ITYPE(ALU, RN, RT);
    }
}
