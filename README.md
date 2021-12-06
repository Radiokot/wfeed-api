# Warm Feed API
Implements read-only API for the [Warm feed app](https://radiokot.com.ua/p/feed/). 
The database is being maintanined by the [Updater](https://github.com/Radiokot/wfeed-updater/).

There are both legacy and JSONAPI implementations.

## Endpoints
Some day I'll create an OpenApi spec for it...

All the JSONAPI dates are in UTC and formatted unsing ISO 8601 instant format, e.g. `2011-12-03T10:15:30Z`.
All the legacy dates are in UTC unixtime.

### Status
`GET /v2/`

Returns current feed status.
```javascript
{
  "data": {
    "type": "status",
    "id": "1638803137003",
    "attributes": {
      "last_post_date": {
        "INTERNAL": null,
        "VK": "2021-12-06T15:00:06Z",
        "TUMBLR": "2021-12-06T15:01:46Z"
      }
    }
  }
}
```

### Categories
`GET /v2/categories/`

Returns all the available categories with localized descriptions and author count.
```javascript
{
  "data": [
    {
      "type": "categories",
      "id": "1",
      "attributes": {
        "thumb_url": "http://feed.radiokot.com.ua/thumb/art.jpg",
        "author_count": 13,
        "description": {
          "ru": "Арт и живопись",
          "uk": "Арт та живопис",
          "en": "Art & Illustration"
        }
      }
    }
  ]
}
```

### Posts
`GET /v2/posts/`

Returns a page of filtered posts ordered by date from new to old. Authors and attachments are included.

|Query param|Example|Description|Default|
|-----------|-------|-----------|-------|
|filter[categories]|1,2,3|List of comma-separated category ID from which to return posts|0,1,2,3|
|page[limit]|15|How many posts to return in a page. From 1 to 50|20|
|page[cursor]|2021-12-06T15:01:46Z|Date to return posts only older than it (page last post date, see response meta)|Last post date|

```javascript
{
  "data": [
    {
      "type": "posts",
      "id": "56_1638778850000",
      "attributes": {
        "api_id": "669808171485888512",
        "date": "2021-12-06T08:20:50Z",
        "text": "Luna",
        "url": "https://the-wolf-and-moon.tumblr.com/post/669808171485888512/luna"
      },
      "relationships": {
        "author": {
          "data": {
            "type": "authors",
            "id": "56"
          }
        },
        "attachments": {
          "data": [
            {
              "type": "photo-attachments",
              "id": "19673"
            }
          ]
        }
      }
    }
  ],
  "meta": {
    "next": {
      "page[cursor]": "2021-12-06T08:20:50Z"
    }
  },
  "included": [
    {
      "type": "authors",
      "id": "56",
      "attributes": {
        "api_id": "the-wolf-and-moon",
        "site": "TUMBLR",
        "name": "Stargazing Wolf",
        "photo_url": "http://33.media.tumblr.com/avatar_0abd4567ee96_96.png"
      },
      "relationships": {
        "category": {
          "data": {
            "type": "categories",
            "id": "8"
          }
        }
      }
    },
    {
      "type": "photo-attachments",
      "id": "19673",
      "attributes": {
        "api_id": null,
        "width": 2048,
        "height": 2048,
        "url_130": "https://64.media.tumblr.com/784a43001d68c0c4a2cd32bb74c3f42d/dfa8ed214b594b95-68/s100x200/f05d2e187fb625e035dd73d299a0f420ea80f543.jpg",
        "url_604": "https://64.media.tumblr.com/784a43001d68c0c4a2cd32bb74c3f42d/dfa8ed214b594b95-68/s540x810/389f3d766e4167682c1dcd2466431cbf8dd33e97.jpg",
        "url_807": "https://64.media.tumblr.com/784a43001d68c0c4a2cd32bb74c3f42d/dfa8ed214b594b95-68/s640x960/358b5a86231b0d99e05840e124cfdee7515d367e.jpg",
        "url_1280": "https://64.media.tumblr.com/784a43001d68c0c4a2cd32bb74c3f42d/dfa8ed214b594b95-68/s1280x1920/77f5a072c7809d0172910f5e9c3f66b43e11e240.jpg",
        "url_2560": "https://64.media.tumblr.com/784a43001d68c0c4a2cd32bb74c3f42d/dfa8ed214b594b95-68/s2048x3072/82aa7f4f7e965f594ea5f2698b04487bb1a950b5.jpg"
      }
    }
  ]
}
```

### Legacy categories
`GET /categories`

### Legacy posts
`GET /get`

## Run
```bash
DB_HOST=localhost \
DB_PORT=3306 \
DB_NAME=feed \
DB_USER=*** \
DB_PASSWORD=*** \
PORT=**optional** \
java -Dlog4j.configuration=file:build/libs/logging.properties -jar build/libs/api.jar
```
