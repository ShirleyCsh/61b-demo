package adventure;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import helpers.CaptureSystemOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import static com.google.common.truth.Truth.assertWithMessage;

@CaptureSystemOutput
public class AdventureGameTests {
    public static final String PREFIX_PATH = "tests/data/";

    public static final Map<String, Integer> STAGE_TO_LINE_CORRECT = Map.of(
            "BeeCountingStage", 14,
            "SpeciesListStage", 28,
            "PalindromeStage", 36,
            "MachineStage", 56
    );

    public static final Map<String, Integer> STAGE_TO_LINE_INCORRECT = Map.of(
            "BeeCountingStage", 14,
            "SpeciesListStage", 30,
            "PalindromeStage", 39,
            "MachineStage", 59
    );

    @ParameterizedTest
    @DisplayName("Correct inputs")
    @ValueSource(strings = {"BeeCountingStage", "SpeciesListStage", "PalindromeStage", "MachineStage"})
    public void testStageCorrect(String stage, CaptureSystemOutput.OutputCapture capture) {
        assertWithMessage("Game output for correct inputs on " + stage + " does not match")
                .that(runUntilStage(stage, "correctInput.txt", "correctAnswers.txt", capture))
                .isTrue();
    }

    @ParameterizedTest
    @DisplayName("Incorrect inputs")
    @ValueSource(strings = {"BeeCountingStage", "SpeciesListStage", "PalindromeStage", "MachineStage"})
    public void testStageIncorrect(String stage, CaptureSystemOutput.OutputCapture capture) {
        assertWithMessage("Game output for incorrect inputs on " + stage + " does not match")
                .that(runUntilStage(stage, "incorrectInput.txt", "incorrectAnswers.txt", capture))
                .isTrue();
    }


    public boolean runUntilStage(String stage, String inputFile, String answersFile,
                                 CaptureSystemOutput.OutputCapture capture) {
        runTestGame(inputFile);
        String expected = new In(new File(PREFIX_PATH + answersFile)).readAll();
        String expectedClean = expected.replace("\r\n", "\n").strip();
        String captureClean = capture.toString().replace("\r\n", "\n").strip();
        int stageEndLine = inputFile.equals("correctInput.txt") ?
                STAGE_TO_LINE_CORRECT.get(stage) :
                STAGE_TO_LINE_INCORRECT.get(stage);
        return Arrays.stream(captureClean.split("\n")).toList().subList(0, stageEndLine)
                .equals(Arrays.stream(expectedClean.split("\n")).toList().subList(0, stageEndLine));
    }

    public void runTestGame(String filePath) {
        In in = new In(new File(PREFIX_PATH + filePath));
        StdRandom.setSeed(1337);
        AdventureGame game = new AdventureGame(in);
        game.play();
    }

}
