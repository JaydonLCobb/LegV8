import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Disassembler {
    
    private static final Map<Integer, Integer> hMap = new HashMap<>();

    public static Map<Integer, Integer> valueByKey() {
        hMap.put(0b00000, 0);
        hMap.put(0b00001, 1);
        hMap.put(0b00010, 2);
        hMap.put(0b00011, 3);
        hMap.put(0b00100, 4);
        hMap.put(0b00101, 5);
        hMap.put(0b00110, 6);
        hMap.put(0b00111, 7);
        hMap.put(0b01000, 8);
        hMap.put(0b01001, 9);
        hMap.put(0b01010, 10);
        hMap.put(0b01011, 11);
        hMap.put(0b01100, 12);
        hMap.put(0b01101, 13);
        hMap.put(0b01110, 14);
        hMap.put(0b01111, 15);
        hMap.put(0b10000, 16);
        hMap.put(0b10001, 17);
        hMap.put(0b10010, 18);
        hMap.put(0b10011, 19);
        hMap.put(0b10100, 20);
        hMap.put(0b10101, 21);
        hMap.put(0b10110, 22);
        hMap.put(0b10111, 23);
        hMap.put(0b11000, 24);
        hMap.put(0b11001, 25);
        hMap.put(0b11010, 26);
        hMap.put(0b11011, 27);
        hMap.put(0b11100, 28);
        hMap.put(0b11101, 29);
        hMap.put(0b11110, 30);
        hMap.put(0b11111, 31);
        return hMap;
    }


    // Only using string type to write to file
    public static String disassemble(byte[] arr) {
        String output = "";
        String complete = "";
        for (int i = 3; i < arr.length; i += 4) {
            int bitNum = (Byte.toUnsignedInt(arr[i - 3]) << 24) | (Byte.toUnsignedInt(arr[i - 2]) << 16) | (Byte.toUnsignedInt(arr[i - 1]) << 8) | Byte.toUnsignedInt(arr[i]);
            switch (bitNum & 0b11111111_11100000_00000000_00000000) {
                case 0b10001011_00000000_00000000_00000000:
                    int RD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int RM = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int RN = (bitNum & 0b00000000_00011111_00000000_00000000) >> 16;
                    int XRD = valueByKey().get(RD);
                    int XRM = valueByKey().get(RM);
                    int XRN = valueByKey().get(RN);
                    output = "ADD X" + XRD + ", X" + XRM + ", X" + XRN;
                    break;
                case 0b11001011_00000000_00000000_00000000:
                    int sRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int sRN = (bitNum & 0b00000000_00011111_00000000_00000000) >> 16;
                    int sRM = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xSRD = valueByKey().get(sRD);
                    int xSRM = valueByKey().get(sRM);
                    int xSRN = valueByKey().get(sRN);
                    output = "SUB X" + xSRD + ", X" + xSRM + ", X" + xSRN;
                    break;
                case 0b11101011_00000000_00000000_00000000:
                    int ssRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int ssRN = (bitNum & 0b00000000_00011111_00000000_00000000) >> 16;
                    int ssRM = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xSSRD = valueByKey().get(ssRD);
                    int xSSRM = valueByKey().get(ssRM);
                    int xSSRN = valueByKey().get(ssRN);
                    output = "SUBS X" + xSSRD + ", X" + xSSRM + ", X" + xSSRN;
                    break;
                case 0b10001010_00000000_00000000_00000000:
                    int anRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int anRN = (bitNum & 0b00000000_00011111_00000000_00000000) >> 16;
                    int anRM = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xANRD = valueByKey().get(anRD);
                    int xANRM = valueByKey().get(anRM);
                    int xANRN = valueByKey().get(anRN);
                    output = "AND X" + xANRD + ", X" + xANRM + ", X" + xANRN;
                    break;
                case 0b10101010_00000000_00000000_00000000:
                    int oRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int oRM = (bitNum & 0b00000000_00011111_00000000_00000000) >> 16;
                    int oRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xORD = valueByKey().get(oRD);
                    int xORM = valueByKey().get(oRM);
                    int xORN = valueByKey().get(oRN);
                    output = "ORR X" + xORD + ", X" + xORM + ", X" + xORN;
                    break;
                case 0b11001010_00000000_00000000_00000000:
                    int eRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int eRN = (bitNum & 0b00000000_00011111_00000000_00000000) >> 16;
                    int eRM = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xERD = valueByKey().get(eRD);
                    int xERM = valueByKey().get(eRM);
                    int xERN = valueByKey().get(eRN);
                    output = "EOR X" + xERD + ", X" + xERM + ", X"  + xERN;
                    break;
                case 0b11010110_00000000_00000000_00000000:
                    int brRM = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xBRM = valueByKey().get(brRM);
                    output = "BR X" + xBRM;
                    break;
                case 0b00000000_00000000_00000000_00000000:
                    break;
                case 0b11111111_10100000_00000000_00000000:
                    output = "PRNT X" + valueByKey().get((bitNum & 0b00000000_00000000_00000000_00011111));
                    break;
                case 0b11111111_10000000_00000000_00000000:
                    output = "PRNL";
                    break;
                case 0b11111111_11000000_00000000_00000000:
                    output = "DUMP";
                    break;
                case 0b11111111_11100000_00000000_00000000:
                    output = "HALT";
                    break;
            }
            switch (bitNum & 0b11111111_11000000_00000000_00000000) {
                case 0b10010001_00000000_00000000_00000000:
                    int aiALU = (bitNum & 0b00000000_00011111_1111110_00000000) >> 10;
                    int aiRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int aiRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xAIRD = valueByKey().get(aiRD);
                    int xAIRN = valueByKey().get(aiRN);
                    output = "ADDI X" + xAIRD + ", X" + xAIRN + ", #" + aiALU;
                    break;
                case 0b10010010_00000000_00000000_00000000:
                    int adiALU = (bitNum & 0b00000000_00011111_1111110_00000000) >> 8;
                    int adiRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int adiRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xADIRD = valueByKey().get(adiRD);
                    int xADIRN = valueByKey().get(adiRN);
                    output = "ANDI X" + xADIRD + ", X" + xADIRN + ", #" + adiALU;
                    break;
                case 0b11010001_00000000_00000000_00000000:
                    int siALU = ((bitNum & 0b00000000_00011111_1111110_00000000) >> 10);
                    int siRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int siRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xSIRD = valueByKey().get(siRD);
                    int xSIRN = valueByKey().get(siRN);
                    output = "SUBI X" + xSIRD + ", X" + xSIRN + ", #" + siALU;
                    break;
                case 0b11110001_00000000_00000000_00000000:
                    int sbiALU = (bitNum & 0b00000000_00011111_1111110_00000000) >> 10;
                    int sbiRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int sbiRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xSBIRD = valueByKey().get(sbiRD);
                    int xSBIRN = valueByKey().get(sbiRN);
                    output = "SUBIS X" + xSBIRD + ", X" + xSBIRN + ", #" + sbiALU;
                    break;
                case 0b11001010_00000000_00000000_00000000:
                    int eoALU = (bitNum & 0b00000000_00011111_1111110_00000000) >> 8;
                    int eoRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int eoRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xEORD = valueByKey().get(eoRD);
                    int xEORN = valueByKey().get(eoRN);
                    output = "EORI X" + xEORD + ", X" + xEORN + ", #" + eoALU;
                    break;
                case 0b10110010_00000000_00000000_00000000:
                    int orALU = (bitNum & 0b00000000_00011111_1111110_00000000) >> 8;
                    int orRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int orRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int xORD = valueByKey().get(orRD);
                    int xORN = valueByKey().get(orRN);
                    output = "ORRI X" + xORD + ", X" + xORN + ", #" + orALU;
                    break;
                case 0b11010011_01000000_00000000_00000000:
                    int lsRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int lsRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int lsSH = ((bitNum & 0b00000000_00000000_11111100_00000000)) / 1024;
                    int xLSRD = valueByKey().get(lsRD);
                    int xLSRN = valueByKey().get(lsRN);
                    output = "LSL X" + xLSRD + ", X" + xLSRN + ", #" + lsSH;
                    break;
                case 0b11010011_01100000_00000000_00000000:
                    int lrRD = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int lrRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int lrSH = ((bitNum & 0b00000000_00000000_11111100_00000000));
                    int xLRRD = valueByKey().get(lrRD);
                    int xLRRN = valueByKey().get(lrRN);
                    output = "LSR X" + xLRRD + ", X" + xLRRN + ", #" + lrSH;
                    break;
            }
            switch (bitNum & 0b11111111_00000000_00000000_00000000) {
                case 0b00010100_00000000_00000000_00000000:
                    int bOne = (bitNum & 0b00000011_11111111_11111111_11111111);
                    output = "B " + bOne;
                    break;
                    case 0b10111111_00000000_00000000_00000000:
                        int bTwo = (bitNum & 0b00000011_11111111_11111111_11111111);
                        output = "b " + bTwo;
                        break;
                case 0b10010100_00000000_00000000_00000000:
                    int blOne = (bitNum & 0b00000011_11111111_11111111_11111111);
                    output = "BL " + blOne;
                    break;
                case 0b10010111_11100000_00000000_00000000:
                    int blTwo = (bitNum & 0b00000011_11111111_11111111_11111111);
                    output = "Bl " + blTwo;
                    break;
            }
            switch (bitNum & 0b11111111_11100000_00000000_00000000) {
                case 0b11111000_01000000_00000000_00000000:
                    int ldDT = (bitNum & 0b00000000_00011111_11110000_00000000);
                    int ldRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int ldRT = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int xLDRT = valueByKey().get(ldRT);
                    int xLDRN = valueByKey().get(ldRN);
                    output = "LDUR X" + xLDRT + ", [X" + xLDRN + ", #" + ldDT + "]";
                    break;
                case 0b11111000_00000000_00000000_00000000:
                    int stDT = (bitNum & 0b00000000_00011111_11110000_00000000) >> 16;
                    int stRN = ((bitNum & 0b00000000_00000000_00000011_11100000) << 3) >> 8;
                    int stRT = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int xSTRT = valueByKey().get(stRT);
                    int xSTRN = valueByKey().get(stRN);
                    output = "STUR X" + xSTRT + ", [X" + xSTRN + ", #" + stDT + "]";
                    break;
            }
            switch (bitNum & 0b11111111_00000000_00000000_00000000) {
                case 0b11101011_00000000_00000000_00000000:
                    int bEQ = (bitNum & 0b00000011_11111111_11111111_11110000);
                    output = "B.EQ " + bEQ;
                    break;
                case 0b10110000_00000000_00000000_00000000:
                    int bLT = (bitNum & 0b00000011_11111111_11111111_11110000);
                    output = "B.LT " + bLT;
                    break;
                case 0b10110101_00000000_00000000_00000000:
                    int CBNZ = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int CBNZADDR = (bitNum & 0b00000011_11111111_11111111_11110000);
                    int xCBNZ = valueByKey().get(CBNZ);
                    output = "CBNZ X" + xCBNZ + ", #" + CBNZADDR;
                    break;
                case 0b10110100_00000000_00000000_00000000:
                    int CBZ = (bitNum & 0b00000000_00000000_00000000_00011111);
                    int CBZADDR = (bitNum & 0b00000011_11111111_11111111_11110000);
                    int xCBZ = valueByKey().get(CBZ);
                    output = "CBZ X" + xCBZ + ", #" + CBZADDR;
                    break;
            }
            complete += output + "\n";
        }
        return complete;
    }

    public static void main(String[] args) {
        String inputFile = "assignment1.legv8asm.machine";
        String outputFile = "assignment1.legv8asm";
        try {
            byte[] arr = Files.readAllBytes(Paths.get(inputFile));
            String disassembledProgram = disassemble(arr);
            Files.write(Paths.get(outputFile), disassembledProgram.getBytes());
            System.out.println("Disassembled program written to " + outputFile);
//            System.out.println(Arrays.toString(arr));
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
    }