public class Util {
    /**
     * Test dirver
     */
    public static void main(String[] args) {
        boolean check = true;
        String string1 = "asdf";
        String string2 = rightPadString(string1, 10);

        if (string1 != "asdf")
            check = false;
        if (string2 != "asdf      ")
            check = false;

        if (!check)
            System.out.println("Util Test Pass");
        else {
            System.out.println("Util Test Fail");
        }
    }

    public static String rightPadString(String string, int length) {
        char[] chars = new char[length];
        int index = 0;
        while (index < length) {
            if (index < string.length()) {
                chars[index] = string.charAt(index);
            } else {
                chars[index] = ' ';
            }
            index += 1;
        }
        return new String(chars);
    }

}
