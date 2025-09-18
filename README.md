# 👻 Mouse Ghost — Anti-Idle Utility
![logo](https://github.com/imgurujeet/MouseGhost/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp)

>Mouse Ghost displays a subtle moving screen pattern that triggers movement in red-light (visible LED) optical mice placed on the display. The app is hardware-only — it does not install drivers, connect to the host computer, or run services on the monitored system.
> Intended for benign situations such as presentations, demos, device testing, or avoiding accidental lock during long reads. This project is not intended to help break rules, evade monitoring, or misrepresent activity.

## ⚠️ Important — Read Before Using

Use at your own risk. The developer assumes no responsibility for misuse or for consequences arising from using this tool.

Do not use Mouse Ghost to violate workplace policies, institutional rules, or laws. If you are unsure whether a use is allowed, do not use it.

Only use this tool if you understand the ethics and potential consequences of your actions. If you would intentionally hide activity from an employer or organization, do not use Mouse Ghost.

[Download Here](https://github.com/imgurujeet/MouseGhost/releases/download/release/app-release.apk)

## ⚒️ What it does
#### Mouse Ghost renders a moving visual pattern on your screen. When a red-light optical mouse is placed on that portion of the display, the mouse’s optical sensor detects micro-movements and the cursor registers slow, natural motion — without any software connection to the host machine.



## 📱 Screenshots

<img src="app/src/main/res/Assets/ss1.png" width="240"/> <img src="app/src/main/res/Assets/ss2.png" width="240"/> <img src="app/src/main/res/Assets/ss3.png" width="240"/>





## ✨ Key Features  

**Hardware-only interaction** – no drivers, no host-side software, and no network access.  
**Red-light optical mouse compatible** – designed for mice that use a visible red LED sensor.  
**Pattern with vibration effect** – adds micro-shakes to mimic natural hand movement.  
**Sleep control & battery saver** – automatically pauses/resumes patterns to reduce power consumption.  
**Low display intensity & power-aware** – optimized visuals to stay subtle while saving energy.  
**Simple UI** – start a pattern, pick type/speed, then place your mouse on the screen.  


## ✅ Legitimate Example Use Cases  

- Preventing accidental screen lock during presentations or demos.  
- Avoiding timeouts while reading or doing long, uninterrupted work where you remain present.  
- Testing or calibrating optical mouse behavior for hardware development or QA.  
- Accessibility scenarios where small continuous motion supports an assistive setup.
---
## ⚠️ Compatibility & Safety Notes  

- **Works only with visible red-LED optical mice.** Laser and IR mice are typically incompatible.  
- Results vary by mouse model, display technology, and ambient light. You may need to adjust brightness/contrast.  


## 📂 Project Structure  

```plaintext
com.imgurujeet.mouseghost
│
├── core/                     → Core utilities & constants
│   ├── Constants.kt
│   └── PreferenceManager.kt
│
├── presentation/             → App presentation layer
│   │
│   ├── navigation/           → Navigation setup
│   │   └── AppNavigation.kt
│   │
│   └── screens/              → UI screens
│       ├── components/       → Reusable UI components
│       │   └── SettingCards.kt
│       │
│       ├── FAQScreen.kt
│       ├── HomeScreen.kt
│       ├── OnBoardingScreen.kt
│       └── SettingScreen.kt
│
├── ui/theme/                 → App theme setup
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
│
├── MainActivity.kt           → App entry point
└── WebViewActivity.kt        → WebView-based activity


```


## License

[MIT](LICENSE.md)


## Contributing

Want to help? Please check out the notes in [CONTRIBUTE.md](CONTRIBUTE.md) first.



## ❓ FAQ  

**Q1: Does Mouse Ghost install anything on my system?**  
No. Mouse Ghost is 100% hardware-based. It only displays patterns on your screen. Your mouse interprets those patterns as movement. Nothing is installed or connected to your computer.  

**Q2: Which mice are supported?**  
Mouse Ghost only works with **visible red-LED optical mice**. Laser or infrared (IR) mice typically cannot detect the on-screen patterns.  

**Q3: Can I change the movement style or speed?**  
Yes. The app provides multiple built-in patterns (circle, wave, micro-jitter, vibration effect) and allows you to adjust speed/intensity.  

**Q4: Will this save or drain my battery?**  
Mouse Ghost includes **sleep control and low-intensity visuals** to minimize power consumption. It’s generally more battery-friendly than hardware jigglers.  

**Q5: Is this undetectable by monitoring tools?**  
Mouse Ghost does not connect to or alter your system, so there’s nothing for software to detect. However, how cursor movement appears depends on your environment. **Use at your own risk** and only in situations where it’s appropriate.  

**Q6: Is Mouse Ghost free to use?**  
Yes. The project is open-source under the **MIT License**. You can use, modify, or contribute as long as you respect the license terms.  


---

## 🌐 Connect with Me  

<p align="center">
  <a href="https://x.com/imgurujeet">
    <img src="https://cdn-icons-png.flaticon.com/512/5968/5968830.png" width="24" height="24" alt="X" style="margin: 0 8 px;">
  </a>
  <a href="https://instagram.com/imgurujeet">
    <img src="https://cdn-icons-png.flaticon.com/512/2111/2111463.png" width="24" height="24" alt="Instagram" style="margin: 0 8 px;">
  </a>
  <a href="https://github.com/imgurujeet">
    <img src="https://cdn-icons-png.flaticon.com/512/733/733553.png" width="24" height="24" alt="GitHub" style="margin: 0 8 px;">
  </a>
</p>






