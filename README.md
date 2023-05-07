# Számítástechnikai eszköz webshop

## Rövid bemutatás:
SZTE Mobil Alkalmazásfejlesztés tantárgyra készített kötelező programom.
## Segítség a pontozáshoz:
Firebase autentikáció meg van valósítva:
- Be lehet jelentkezni: [LoginFragment.java:40](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/java/com/example/computerwebshop/ui/LoginFragment.java#L40)
- és regisztrálni: [RegisterFragment.java:61](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/java/com/example/computerwebshop/ui/RegisterFragment.java#L61)

---

Adatmodell definiálása (class vagy interfész formájában): [model](https://github.com/Peter61045720/Computer-Webshop-Android/tree/master/app/src/main/java/com/example/computerwebshop/model) mappa tartalma, például: [User.java](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/java/com/example/computerwebshop/model/User.java)

---

Legalább 3 különböző activity vagy fragmens használata: MainActivity + [ui](https://github.com/Peter61045720/Computer-Webshop-Android/tree/master/app/src/main/java/com/example/computerwebshop/ui) mappa tartalma

---

Beviteli mezők beviteli típusa megfelelő (jelszó kicsillagozva, email-nél megfelelő billentyűzet jelenik meg stb.) 
- [fragment_login.xml](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/res/layout/fragment_login.xml)
- [fragment_register.xml](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/res/layout/fragment_register.xml)

---

ConstraintLayout és még egy másik layout típus használata:
- LinearLayout: [fragment_login.xml](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/res/layout/fragment_login.xml)
- DrawerLayout, CoordinatorLayout, AppBarLayout: [activity_main.xml](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/res/layout/activity_main.xml)

---

Reszponzív:
- különböző kijelző méreteken is jól jelennek meg a GUI elemek (akár tableten is): ezt a feltételt talán teljesíti az alkalmazás
- elforgatás esetén is igényes marad a layout: -

---

Legalább 2 különböző animáció használata: [anim](https://github.com/Peter61045720/Computer-Webshop-Android/tree/master/app/src/main/res/anim) mappa, és a [nav_graph.xml](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/res/navigation/nav_graph.xml)-ben használtam ezeket a fragment-ek közötti váltásnál

---

Intentek használata: navigáció meg van valósítva az activityk/fragmensek között (minden activity/fragmens elérhető):
- Mivel nálam csak 1db activity van, ezért nem Intent-et, hanem [NavController](https://developer.android.com/reference/androidx/navigation/NavController)-t használtam a fragment-ek között váltáshoz
- Navigáció cél-fragment-jeihez lásd: [nav_graph.xml](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/res/navigation/nav_graph.xml)

---

Legalább egy Lifecycle Hook használata a teljes projektben:
- onCreate nem számít
- az alkalmazás funkcionalitásába értelmes módon beágyazott, azaz pl. nem csak egy logolás
- RecyclerView (...)

---

Legalább egy olyan androidos erőforrás használata, amihez kell android permission: -

---

Legalább egy notification vagy alam manager vagy job scheduler használata: notification manager (...)

---

CRUD műveletek mindegyike megvalósult és az adatbázis műveletek a konvenciónak megfelelően külön szálon történnek:
- Create: [ProfilePhotoFragment.java:83](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/java/com/example/computerwebshop/ui/ProfilePhotoFragment.java#L83)
- Read: [MainActivity.java:98](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/java/com/example/computerwebshop/MainActivity.java#L98)
- Delete: [ProfileFragment.java:48](https://github.com/Peter61045720/Computer-Webshop-Android/blob/master/app/src/main/java/com/example/computerwebshop/ui/ProfileFragment.java#L48)

---

Legalább 2 komplex Firestore lekérdezés megvalósítása, amely indexet igényel (ide tartoznak: where feltétel, rendezés, léptetés, limitálás): (...)