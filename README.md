# ListClasses
Annotation processor utility to index all classes being compiled.

### Depending on the Library:
Add the library as both a compile-time dependency and annotation processor. ListClasses is not required at runtime, all of it's annotations and classes are only required during compile-time.

Example build.gradle:

```groovy
plugins {
    id "net.ltgt.apt" version "0.10"
}

group 'example'
version '0.0.0'

apply plugin: 'java'
sourceCompatibility = 1.8

repositories {
    maven {
        name "jitpack.io"
        url "https://jitpack.io"
    }
}

dependencies {
    apt "com.github.SocraticPhoenix:ListClasses:master-SNAPSHOT"
    compileOnly "com.github.SocraticPhoenix:ListClasses:master-SNAPSHOT"
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
}
```

### How to Use:
Through Java's annotation processing, ListClasses attempts to discover all the classes being compiled, and will automatically add them to a class index. To obtain this class index, a class must be annotated with the `@ListClass` annotation. `@ListClass` has three arguments:

 - ignore: defaults to false; if this is true, the class will not be included in the generated class index
 - supplyList: defaults to false; if this is true, a copy of the class index will be written to the same package as the class the `@ListClass` annotation is applied to
 - listName: defaults to "class_index.txt"; this sets the name of the file that will be supplied if supplyList is true

Any class that has the `@ListClass` annotation with `supplyList` set to true can access the class index through `Class#getResource` or `Class#getResourceAsStream`. If no class has a `@ListClass` annotation with `supplyList` set to true, no class index will be written.

Example project structure:
```plain
- A.java
- B.java with @ListClass(ignore = true)
- com.example.C.java
- com.other.Foo.java with @ListClass(supplyList = true)
```

Resulting class index:
```plain
A
com.example.C
com.other.Foo
```

Resulting .jar structure:
```plain
- A.class
- B.class
- com.example.C.class
- com.other
--- Foo.class
--- class_index.txt
```
