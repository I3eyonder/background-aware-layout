# Background Aware Layout

<img alt="demo" height="534" src="screenshots/background-aware-layout.gif" width="300"/>

## Integration

### Gradle:

This library is available on [jitpack.io](https://jitpack.io/#I3eyonder/background-aware-layout).

###### Step 1. Add the JitPack repository to your build file

Add this in your root build.gradle at the end of repositories:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

###### Step 2. Add the dependency

[![](https://jitpack.io/v/I3eyonder/background-aware-layout.svg)](https://jitpack.io/#I3eyonder/background-aware-layout)

```gradle
implementation 'com.github.I3eyonder:background-aware-layout:Tag'
```

#### Xml

```
<com.hieupt.view.BackgroundAwareLinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@color/white">

    <TextView
        android:id="@+id/contentView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="5dp"
        android:gravity="center"
        android:textSize="16sp"
        app:layout_backgroundAwareScaleType="center|fitCener|fitXY|fitStart|fitEnd"
        app:layout_backgroundAwareMode="clear|tint"
        app:layout_backgroundAwareTint="@color"
        app:layout_backgroundAware="@drawable" />
</com.hieupt.view.BackgroundAwareLinearLayout>

<com.hieupt.view.BackgroundAwareRelativeLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/white">
                //Content view goes here
</com.hieupt.view.BackgroundAwareRelativeLayout>

<com.hieupt.view.BackgroundAwareFrameLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/white">
                //Content view goes here
</com.hieupt.view.BackgroundAwareFrameLayout>

<com.hieupt.view.BackgroundAwareConstraintLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/white">
                //Content view goes here
</com.hieupt.view.BackgroundAwareConstraintLayout>
```

Please
check [view package](https://github.com/hieupham1993/background-aware-layout/tree/master/backround-aware-layout/src/main/java/com/hieupt/view)
for all supported layouts

#### Code

For kotlin, using view extensions

```kotlin
contentView.backgroundAware = Drawable
contentView.backgroundAwareTint = ColorStateList
contentView.backgroundAwareMode = BackgroundAwareMode.CLEAR|BackgroundAwareMode.TINT
contentView.backgroundAwareScaleType =
    BackgroundAwareScaleType.CENTER|BackgroundAwareScaleType.FIT_CENTER|BackgroundAwareScaleType.FIT_XY|BackgroundAwareScaleType.FIT_START|BackgroundAwareScaleType.FIT_END
contentView.backgroundAwarePathCreator = IClipPathCreator
```

For java

```java
ViewExtKt.setBackgroundAware(contentView,Drawable);
        ViewExtKt.setBackgroundAwareTint(contentView,ColorStateList);
        ViewExtKt.setBackgroundAwareMode(contentView,BackgroundAwareMode.CLEAR|BackgroundAwareMode.TINT);
        ViewExtKt.setBackgroundAwareScaleType(contentView,BackgroundAwareScaleType.CENTER|BackgroundAwareScaleType.FIT_CENTER|BackgroundAwareScaleType.FIT_XY|BackgroundAwareScaleType.FIT_START|BackgroundAwareScaleType.FIT_END);
        ViewExtKt.setBackgroundAwarePathCreator(contentView,IClipPathCreator);
```

There are
many [`IClipPathCreator`](https://github.com/hieupham1993/background-aware-layout/blob/master/backround-aware-layout/src/main/java/com/hieupt/view/graphic/IClipPathCreator.kt)
implemented available such as:

- [`RoundRectClipPathCreator`](https://github.com/hieupham1993/background-aware-layout/blob/master/backround-aware-layout/src/main/java/com/hieupt/view/graphic/RoundRectClipPathCreator.kt)
- [`StarClipPathCreator`](https://github.com/hieupham1993/background-aware-layout/blob/master/backround-aware-layout/src/main/java/com/hieupt/view/graphic/StarClipPathCreator.kt)
- [`TriangleClipPathCreator`](https://github.com/hieupham1993/background-aware-layout/blob/master/backround-aware-layout/src/main/java/com/hieupt/view/graphic/TriangleClipPathCreator.kt)
- ...

Please take a look
at [graphic package](https://github.com/hieupham1993/background-aware-layout/tree/master/backround-aware-layout/src/main/java/com/hieupt/view/graphic)
for details

## Customization

You can implement your
own [`IClipPathCreator`](https://github.com/hieupham1993/background-aware-layout/blob/master/backround-aware-layout/src/main/java/com/hieupt/view/graphic/IClipPathCreator.kt)
for a custom shape of background. You can
refer [`RoundRectClipPathCreator`](https://github.com/hieupham1993/background-aware-layout/blob/master/backround-aware-layout/src/main/java/com/hieupt/view/graphic/RoundRectClipPathCreator.kt)
for example of implementation.
