# Java 3D Utils

This is a Mavenized version of the latest
[Java 3D](https://en.wikipedia.org/wiki/Java_3D) `j3dutils` component,
[built on JOGL 2.x](http://forum.jogamp.org/Java3D-now-works-with-JOGL-2-0-td3732206.html),
with a renamed package prefix to avoid conflicts with old versions of Java 3D.

## Background and rationale

Historically, Java 3D was installed as an extension to the Java Runtime
Environment, meaning that JAR files and native libraries were placed in the
`lib/ext` directory of the JRE installation, or manually appended to the Java
extensions via the `java.ext.dirs` system property.

However, that approach has many downsides:

* Users must install Java 3D manually, independent of the application.
* Thus, at runtime, the application cannot manage the version of Java 3D in
  the same way that it manages versions of its regular dependencies.
* Similarly, at build time, the Java 3D dependency must be treated
  specially (e.g., with Maven, using `provided` scope in the POM).

These days, Java 3D 1.6 is [built on top of
JOGL 2.x](http://forum.jogamp.org/Java3D-now-works-with-JOGL-2-0-td3732206.html),
and [available on GitHub](https://github.com/hharrison/java3d-core). But old
installations of Java 3D still lurk, waiting to disrupt applications at
runtime: libraries present on the Java extensions path take precedence over
those on the regular class path.

Failure to manage Java 3D installations as needed can result in cryptic
version-skew-related error messages, such as `NoSuchMethodError` or
even native-library-related errors, including JVM crashes. This situation is
especially prevalent on OS X, where Java 3D 1.3 was pre-installed in
`/System/Library/Java/Extensions` on older versions of the OS, and left in
place after OS upgrades (despite Java itself being uninstalled).

Furthermore, OS X 10.11 "El Capitan" introduced a new security feature called
System Integrity Protection (SIP) which prevents users from deleting the
obsolete Java 3D libraries from `/System/Library/Java/Extensions`. And the
default extensions path of Oracle Java 8 includes this directory, making it
impossible to use Java 3D with Java 8 out of the box in this scenario.

This fork of Java 3D avoids the issue by changing the package prefix, so that
legacy code can be easily refactored to use it without danger from the obsolete
versions of Java 3D.

## Project status

This project is a temporary fork, until upstream Java 3D makes some progress.
See:
* [Java 3D: Use Maven to build, and publish Maven artifacts](http://forum.jogamp.org/Java-3D-Use-Maven-to-build-and-publish-Maven-artifacts-tp4035555p4035810.html)

## How to use it

To use from your Maven project, add the following dependency to your POM:

```xml
<dependency>
  <groupId>org.scijava</groupId>
  <artifactId>j3dutils</artifactId>
  <version>1.6.0-scijava-2</version>
</dependency>
```

## Related projects

* https://github.com/scijava/java3d-core
* https://github.com/scijava/vecmath
