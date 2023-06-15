public class DTYPE {
    int i;
    int j;
    int k;
    int l;
    public DTYPE(int i, int j, int k, int l){
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
    }

    public static DTYPE setDType(byte[] arr, int index){
        int a = Byte.toUnsignedInt(arr[index + 1]);
        int b = Byte.toUnsignedInt(arr[index + 2]);

        int ADDR = ((a << 1) & 0xFFF) | (b >> 4);
        int OP = (b >> 1) & 0x7;
        int RN = ((b & 0x1) << 4) | ((arr[index + 3] >> 4) & 0xF);
        int RD = arr[index + 3] & 0xF;

        return new DTYPE(ADDR, OP, RN, RD);
    }
}
