public class BTYPE {
    int i;
    public BTYPE(int i){
        this.i = i;
    }

    public static BTYPE setBType(byte[] arr, int index){
        int a = Byte.toUnsignedInt(arr[index]);
        int b = Byte.toUnsignedInt(arr[index + 1]);
        int c = Byte.toUnsignedInt(arr[index + 2]);
        int d = Byte.toUnsignedInt(arr[index + 3]);

        int output = (a << 26) | (b << 10) | (c << 8) | d;

        return new BTYPE(output);
    }
}
