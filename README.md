# tdd-boilerplate
Android Test-Driven-Development boilerplate for apps

NOTES FOR sample MODULE:

- An AAR file needs to be generated from the app project:

    + Open the app build.gradle file, then Build > Make Module 'app'
    
    + After any update in the app module, this must be repeated

The apply plugin in app build.gradle file must change:

    from apply plugin: 'com.android.application'
    to
    apply plugin: 'com.android.library'

In the sample build.gradle file:

    dependencies {
        ...
        implementation project(path: ':app')

        // NOTE: DO NOT EVER USE THIS
        implementation project(path: ':app', config: 'default')

        ...
    }
