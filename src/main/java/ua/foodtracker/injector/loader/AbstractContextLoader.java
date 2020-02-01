package ua.foodtracker.injector.loader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public abstract class AbstractContextLoader {
    protected void loadBeans(String[] packagesNames) throws ReflectiveOperationException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        for (String packageName : packagesNames) {
            List<File> dirs = new ArrayList<>();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }
            for (File directory : dirs) {
                loadBeans(directory, packageName);
            }
        }
    }

    protected abstract void loadBean(Class<?> c) throws ReflectiveOperationException;

    private void loadBeans(File directory, String packageName) throws ReflectiveOperationException {
        if (!directory.exists()) {
            return;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                loadBeans(file, packageName + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                Class<?> c = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                loadBean(c);
            }
        }
    }
}
