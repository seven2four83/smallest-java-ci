package acime;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.Map;
import java.io.*;
import java.nio.file.*;

public class BasicRuntimeFactory implements RuntimeFactory {
    public Runtime makeRuntime() {
	return Runtime.getRuntime();
    }
}
