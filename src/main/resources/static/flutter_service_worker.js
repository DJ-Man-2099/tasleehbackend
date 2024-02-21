'use strict';
const MANIFEST = 'flutter-app-manifest';
const TEMP = 'flutter-temp-cache';
const CACHE_NAME = 'flutter-app-cache';

const RESOURCES = {"assets/AssetManifest.json": "cf4bcdc75d3e57e5b68e8cc6fab1dcc4",
"assets/AssetManifest.smcbin": "6ec82cabac3ff8a07fa4e5c721afb6e7",
"assets/assets/fonts/NotoKufiArabic-Black.ttf": "a65055129014a2fc07afe186821dce7f",
"assets/assets/fonts/NotoKufiArabic-Bold.ttf": "6ec1f3c742af5cbdc130f74472e8fd18",
"assets/assets/fonts/NotoKufiArabic-ExtraBold.ttf": "5a37c0191ca8e5fdd4707e6f0ca71b6a",
"assets/assets/fonts/NotoKufiArabic-ExtraLight.ttf": "f327249f042986e1b43137b36054b1ce",
"assets/assets/fonts/NotoKufiArabic-Light.ttf": "b41a7b2337420edbb9d7a67f2cce7dd7",
"assets/assets/fonts/NotoKufiArabic-Medium.ttf": "bb1c7a181d7afbe50e633977fcce8dba",
"assets/assets/fonts/NotoKufiArabic-Regular.ttf": "b93ba790420d890d254e6d5ddbe50657",
"assets/assets/fonts/NotoKufiArabic-SemiBold.ttf": "8ed15e4f31f3b4122fcfb2106db83754",
"assets/assets/fonts/NotoKufiArabic-Thin.ttf": "a0dca8d87f85c0d1ffaaff40161f8e82",
"assets/assets/fonts/NotoSansArabic-Black.ttf": "6729fd5454518cdaf492a2b3fe27cf88",
"assets/assets/fonts/NotoSansArabic-Bold.ttf": "cda675687ed1576b7bda072838c0ed5f",
"assets/assets/fonts/NotoSansArabic-ExtraBold.ttf": "398d2a98487424a3e5051de8cb0fb1c0",
"assets/assets/fonts/NotoSansArabic-ExtraLight.ttf": "9bf46a52b39d0fc53e9a54dacde2e0bb",
"assets/assets/fonts/NotoSansArabic-Light.ttf": "ffcfcc231a05032bca6e0819aa60eacb",
"assets/assets/fonts/NotoSansArabic-Medium.ttf": "21f0c6935e6ad3e4481e94b236624764",
"assets/assets/fonts/NotoSansArabic-Regular.ttf": "75527903c6a793772b02807c5343f4c8",
"assets/assets/fonts/NotoSansArabic-SemiBold.ttf": "6668035e904e215087e94774d5f47c8d",
"assets/assets/fonts/NotoSansArabic-Thin.ttf": "2b1587c2f8be37f7398be7f4f19a8ff1",
"assets/assets/fonts/NotoSansArabic_Condensed-Black.ttf": "f14bd7108661cd47700d5a44a022bd5e",
"assets/assets/fonts/NotoSansArabic_Condensed-Bold.ttf": "fbb783a6ff2aeffdf2a29f5d264496bd",
"assets/assets/fonts/NotoSansArabic_Condensed-ExtraBold.ttf": "1177a7b9d97d8950ca1db767f726b05c",
"assets/assets/fonts/NotoSansArabic_Condensed-ExtraLight.ttf": "10fca32dbec65058e90fd538526b4bbb",
"assets/assets/fonts/NotoSansArabic_Condensed-Light.ttf": "452b14e88276b52e26ac460b9cb2c7e1",
"assets/assets/fonts/NotoSansArabic_Condensed-Medium.ttf": "d24c84084e577fb3bb03517c8a20d26c",
"assets/assets/fonts/NotoSansArabic_Condensed-Regular.ttf": "c01f450f90ada0f820d9cc4d5ea03f95",
"assets/assets/fonts/NotoSansArabic_Condensed-SemiBold.ttf": "b0508904816b5574a2e746447b17f010",
"assets/assets/fonts/NotoSansArabic_Condensed-Thin.ttf": "1861f94a8101745776210ea2261d5754",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-Black.ttf": "4869abae4e7bf986f51df0e55b8a2e60",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-Bold.ttf": "2a34ce4751fb907989564832d51c5fbb",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-ExtraBold.ttf": "2ebdf77ca2b3f99b1573992c3bf11fcc",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-ExtraLight.ttf": "64b11693cdc8bd10467f5c062f5596ee",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-Light.ttf": "39e16bc02ffc25fcd09865fdd85d80f9",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-Medium.ttf": "27267fd3e592898c9d23bd4bb54a731d",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-Regular.ttf": "fe4d7da2189c37c45f5d34b46fdf0c6e",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-SemiBold.ttf": "902fd4a8f6b3ecc8d1fa40f819b22190",
"assets/assets/fonts/NotoSansArabic_ExtraCondensed-Thin.ttf": "789c98e16f99aef60a98f7a151bcc24e",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-Black.ttf": "7b7314d4fd5caf6d6c62bdcdf28d4098",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-Bold.ttf": "114981c5bc640a521c59be521497a6b2",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-ExtraBold.ttf": "a52323f61e2d50da02ba9fc27929269d",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-ExtraLight.ttf": "b8fa618894eebab79f091ba0636a1b0a",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-Light.ttf": "c7f053b72c794ade00e4a825cbdf19db",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-Medium.ttf": "1e4238d991444359b56bcf90c9e12d6f",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-Regular.ttf": "7c40f880a43986fe144bfbf477b9fd3f",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-SemiBold.ttf": "f428b8238a05b4855640dbaf0aecaa0c",
"assets/assets/fonts/NotoSansArabic_SemiCondensed-Thin.ttf": "16f15b0b5ecfb8e809b8f01178ea81f8",
"assets/assets/images/elec.png": "cba170f4dee19ead8ffde616a354eb6c",
"assets/assets/images/image-thumb.png": "32aa01d26d8944952eac129507a6836d",
"assets/assets/images/panner.jpg": "f099bd54b9a099cac1b1993d71c4ee82",
"assets/assets/images/pdf-thumb.png": "e125f67982b4b8e2be93fb8bf6a3cad9",
"assets/assets/images/word-thumb.png": "162f48b2d24376d3b1a30268cb1eab25",
"assets/FontManifest.json": "74e4bec6f026538fc44255c0d3000977",
"assets/fonts/MaterialIcons-Regular.otf": "c0cec3c4383f466d8221c1c470559d76",
"assets/fonts/notokufiarabic/NotoKufiArabic-Bold.ttf": "6ec1f3c742af5cbdc130f74472e8fd18",
"assets/fonts/notokufiarabic/NotoKufiArabic-Regular.ttf": "b93ba790420d890d254e6d5ddbe50657",
"assets/fonts/notosansarabic/NotoSansArabic-Bold.ttf": "cda675687ed1576b7bda072838c0ed5f",
"assets/fonts/notosansarabic/NotoSansArabic-Regular.ttf": "75527903c6a793772b02807c5343f4c8",
"assets/fonts/notosanssymbols/NotoSansSymbols-Bold.ttf": "dfc404e8d43e948cfe61c2be9d0bbec4",
"assets/fonts/notosanssymbols/NotoSansSymbols-Regular.ttf": "fa039888a71e6e4ee1d03de39cad549f",
"assets/fonts/roboto/Roboto-Bold.ttf": "b8e42971dec8d49207a8c8e2b919a6ac",
"assets/fonts/roboto/Roboto-Regular.ttf": "8a36205bd9b83e03af0591a004bc97f4",
"assets/NOTICES": "e6e7d50497fed3dda658bdf9eebba4c3",
"assets/packages/cupertino_icons/assets/CupertinoIcons.ttf": "57d849d738900cfd590e9adc7e208250",
"assets/packages/syncfusion_flutter_datagrid/assets/font/FilterIcon.ttf": "b8e5e5bf2b490d3576a9562f24395532",
"assets/packages/syncfusion_flutter_datagrid/assets/font/UnsortIcon.ttf": "acdd567faa403388649e37ceb9adeb44",
"assets/shaders/ink_sparkle.frag": "f8b80e740d33eb157090be4e995febdf",
"canvaskit/canvaskit.js": "76f7d822f42397160c5dfc69cbc9b2de",
"canvaskit/canvaskit.wasm": "f48eaf57cada79163ec6dec7929486ea",
"canvaskit/chromium/canvaskit.js": "8c8392ce4a4364cbb240aa09b5652e05",
"canvaskit/chromium/canvaskit.wasm": "fc18c3010856029414b70cae1afc5cd9",
"canvaskit/skwasm.js": "1df4d741f441fa1a4d10530ced463ef8",
"canvaskit/skwasm.wasm": "6711032e17bf49924b2b001cef0d3ea3",
"canvaskit/skwasm.worker.js": "19659053a277272607529ef87acf9d8a",
"favicon.png": "27d8cb1da9fb6b6d4b0d4ca92e68719a",
"favicon2.png": "5dcef449791fa27946b3d35ad8803796",
"flutter.js": "6b515e434cea20006b3ef1726d2c8894",
"icons/Icon-192.png": "ac9a721a12bbc803b44f645561ecb1e1",
"icons/Icon-512.png": "96e752610906ba2a93c65f8abe1645f1",
"icons/Icon-maskable-192.png": "c457ef57daa1d16f64b27b786ec2ea3c",
"icons/Icon-maskable-512.png": "301a7604d45b3e739efc881eb04896ea",
"index.html": "2e80a88d25653e28d0e57667d9281842",
"/": "2e80a88d25653e28d0e57667d9281842",
"main.dart.js": "fdc8bf905b40315641299836c2a08cd8",
"manifest.json": "0743129a29e64c3e1df752c4a99f846a",
"version.json": "8a146b57da27de1b8576548726dc2373"};
// The application shell files that are downloaded before a service worker can
// start.
const CORE = ["main.dart.js",
"index.html",
"assets/AssetManifest.json",
"assets/FontManifest.json"];

// During install, the TEMP cache is populated with the application shell files.
self.addEventListener("install", (event) => {
  self.skipWaiting();
  return event.waitUntil(
    caches.open(TEMP).then((cache) => {
      return cache.addAll(
        CORE.map((value) => new Request(value, {'cache': 'reload'})));
    })
  );
});
// During activate, the cache is populated with the temp files downloaded in
// install. If this service worker is upgrading from one with a saved
// MANIFEST, then use this to retain unchanged resource files.
self.addEventListener("activate", function(event) {
  return event.waitUntil(async function() {
    try {
      var contentCache = await caches.open(CACHE_NAME);
      var tempCache = await caches.open(TEMP);
      var manifestCache = await caches.open(MANIFEST);
      var manifest = await manifestCache.match('manifest');
      // When there is no prior manifest, clear the entire cache.
      if (!manifest) {
        await caches.delete(CACHE_NAME);
        contentCache = await caches.open(CACHE_NAME);
        for (var request of await tempCache.keys()) {
          var response = await tempCache.match(request);
          await contentCache.put(request, response);
        }
        await caches.delete(TEMP);
        // Save the manifest to make future upgrades efficient.
        await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
        // Claim client to enable caching on first launch
        self.clients.claim();
        return;
      }
      var oldManifest = await manifest.json();
      var origin = self.location.origin;
      for (var request of await contentCache.keys()) {
        var key = request.url.substring(origin.length + 1);
        if (key == "") {
          key = "/";
        }
        // If a resource from the old manifest is not in the new cache, or if
        // the MD5 sum has changed, delete it. Otherwise the resource is left
        // in the cache and can be reused by the new service worker.
        if (!RESOURCES[key] || RESOURCES[key] != oldManifest[key]) {
          await contentCache.delete(request);
        }
      }
      // Populate the cache with the app shell TEMP files, potentially overwriting
      // cache files preserved above.
      for (var request of await tempCache.keys()) {
        var response = await tempCache.match(request);
        await contentCache.put(request, response);
      }
      await caches.delete(TEMP);
      // Save the manifest to make future upgrades efficient.
      await manifestCache.put('manifest', new Response(JSON.stringify(RESOURCES)));
      // Claim client to enable caching on first launch
      self.clients.claim();
      return;
    } catch (err) {
      // On an unhandled exception the state of the cache cannot be guaranteed.
      console.error('Failed to upgrade service worker: ' + err);
      await caches.delete(CACHE_NAME);
      await caches.delete(TEMP);
      await caches.delete(MANIFEST);
    }
  }());
});
// The fetch handler redirects requests for RESOURCE files to the service
// worker cache.
self.addEventListener("fetch", (event) => {
  if (event.request.method !== 'GET') {
    return;
  }
  var origin = self.location.origin;
  var key = event.request.url.substring(origin.length + 1);
  // Redirect URLs to the index.html
  if (key.indexOf('?v=') != -1) {
    key = key.split('?v=')[0];
  }
  if (event.request.url == origin || event.request.url.startsWith(origin + '/#') || key == '') {
    key = '/';
  }
  // If the URL is not the RESOURCE list then return to signal that the
  // browser should take over.
  if (!RESOURCES[key]) {
    return;
  }
  // If the URL is the index.html, perform an online-first request.
  if (key == '/') {
    return onlineFirst(event);
  }
  event.respondWith(caches.open(CACHE_NAME)
    .then((cache) =>  {
      return cache.match(event.request).then((response) => {
        // Either respond with the cached resource, or perform a fetch and
        // lazily populate the cache only if the resource was successfully fetched.
        return response || fetch(event.request).then((response) => {
          if (response && Boolean(response.ok)) {
            cache.put(event.request, response.clone());
          }
          return response;
        });
      })
    })
  );
});
self.addEventListener('message', (event) => {
  // SkipWaiting can be used to immediately activate a waiting service worker.
  // This will also require a page refresh triggered by the main worker.
  if (event.data === 'skipWaiting') {
    self.skipWaiting();
    return;
  }
  if (event.data === 'downloadOffline') {
    downloadOffline();
    return;
  }
});
// Download offline will check the RESOURCES for all files not in the cache
// and populate them.
async function downloadOffline() {
  var resources = [];
  var contentCache = await caches.open(CACHE_NAME);
  var currentContent = {};
  for (var request of await contentCache.keys()) {
    var key = request.url.substring(origin.length + 1);
    if (key == "") {
      key = "/";
    }
    currentContent[key] = true;
  }
  for (var resourceKey of Object.keys(RESOURCES)) {
    if (!currentContent[resourceKey]) {
      resources.push(resourceKey);
    }
  }
  return contentCache.addAll(resources);
}
// Attempt to download the resource online before falling back to
// the offline cache.
function onlineFirst(event) {
  return event.respondWith(
    fetch(event.request).then((response) => {
      return caches.open(CACHE_NAME).then((cache) => {
        cache.put(event.request, response.clone());
        return response;
      });
    }).catch((error) => {
      return caches.open(CACHE_NAME).then((cache) => {
        return cache.match(event.request).then((response) => {
          if (response != null) {
            return response;
          }
          throw error;
        });
      });
    })
  );
}
