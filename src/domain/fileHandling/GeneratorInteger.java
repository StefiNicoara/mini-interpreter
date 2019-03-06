package domain.fileHandling;

class GeneratorInteger {
    private static int counter = 0;

    static int gen_ID() {
        return counter++;
    }
}
