package com.company;

import java.util.Locale;
import java.util.Scanner;


public class Main {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        // забираем правильное число у клиента
        // и вызываем нужные функции:
        // для целого типа и дробного

        for (;;) {
            try {
                long_int(in.nextLong());
                break;
            } catch (Exception error_first) {
                try {
                    in.useLocale(Locale.FRANCE);
                    float_double(in.nextDouble());
                    break;
                } catch (Exception error_second) {
                    try {
                        in.useLocale(Locale.US);
                        float_double(in.nextDouble());
                        break;
                    } catch (Exception error_third) {
                        System.out.println("Неправильный формат ввода");
                        in.next();
                    }
                }
            }
        }
    }

    static void float_double(double first) { // для вещ. чисел
        double tenth = Math.pow(10, 104);
        String result;
        if (-224 * tenth <= first && first <= 224 * tenth) {
            System.out.println("В каком формате вывести число: 1. float; 2. double?");
            int answer = check_input(2);

            if (answer == 2){
                result = Long.toBinaryString(Double.doubleToLongBits(first));
                result = String.format("%64s", result).replace(' ', '0');
            } else {
                result = Integer.toBinaryString(Float.floatToIntBits((float) (first)));
                result = String.format("%32s", result).replace(' ', '0');
            }
        }
        else {
            System.out.println("Ваше число можно вывести только в формате double.");
            result = Long.toBinaryString(Double.doubleToLongBits(first));
            result = String.format("%64s", result).replace(' ', '0');
        }
        System.out.println(result.replaceAll("(.{4})", "$1 "));
    }

    static void long_int(long number) { // для целых чисел
        // byte short int long
        String[] types = new String[4];
        String result = "";
        if (number <= 127 && number >= -128)
            types = new String[] {"byte", "short", "int", "long"};
        else if (number <= 32767 && number >= -32768)
            types = new String[] {"short", "int", "long"};
        else if (number <= 2147483647 && number >= -2147483648)
            types = new String[] {"int", "long"};
        else {
            System.out.println("Ваше число можно вывести только в формате long.");
            result = String.format("%64s", Long.toBinaryString(number));
        }

        if (result.equals("")){
            System.out.print("В каком формате вывести число: ");
            for (int i = 0; i < types.length; i++)
                System.out.print((i + 1) + ". " + types[i] + " ");
            System.out.println();
            String answer = types[check_input(types.length) - 1];
            result = switch (answer) {
                case "long" -> Long.toBinaryString(number);
                default -> Integer.toBinaryString((int) (number));
            };

            result = switch (answer) {
                case "byte" -> result.length() > 8 ? result.substring(result.length() - 8,
                        result.length()) : String.format("%8s", result);
                case "short" -> result.length() > 16 ? result.substring(result.length() - 16,
                        result.length()) : String.format("%16s", result);
                case "int" -> String.format("%32s", result);
                case "long" -> String.format("%64s", result);
                default -> result;
            };

        }
        System.out.println((result.replace(' ', '0'))
                .replaceAll("(.{4})", "$1 "));
    }

    static int check_input(int length){
        int answer;
        for (; ; ) {
            try {
                answer = in.nextInt();
                if (answer >= 1 && answer <= length)
                    break;
            } catch (RuntimeException ignored) {
            }
            System.out.println("Не верный формат ввода");
        }
        return answer;
    }
}