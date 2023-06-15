public class CBTYPE {
    int i;
    int j;
    public CBTYPE(int i, int j){
        this.i = i;
        this.j = j;
    }

    public static CBTYPE setCBType(byte[] arr, int index){
        int a = Byte.toUnsignedInt(arr[index + 1]);
        int b = Byte.toUnsignedInt(arr[index + 2]);
        int c = Byte.toUnsignedInt(arr[index + 3]);

        int ADDR = ((a & 0x7F) << 4) | (b >> 4);
        int RT = c & 0x1F;

        return new CBTYPE(ADDR, RT);
    }


}
