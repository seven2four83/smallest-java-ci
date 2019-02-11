package acime;

public class GHStatusUtils {

    public enum GHStatusValues{
        pending,
        success,
        failure,
        error
    }

    public static String parseGradleOutput(String gradleOutput){
        String successString = "BUILD SUCCESSFUL in";
        int substringLocation = gradleOutput.indexOf(successString);
        if (substringLocation != -1 && substringLocation > gradleOutput.length() - 100){
            return "success";
        }
        return "failure";
    }
}
