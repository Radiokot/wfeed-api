# Warm Feed API
Implements read-only API for the [Warm feed app](https://radiokot.com.ua/p/feed/). 
The database is being maintanined by the [Updater](https://github.com/Radiokot/wfeed-updater/).

There are both legacy and JSONAPI implementations.

## Endpoints
All the JSONAPI dates are in UTC and formatted unsing ISO 8601 instant format, e.g. `2011-12-03T10:15:30Z`.
All the legacy dates are in UTC unixtime.

Check out [Warm feed JSONAPI reference](http://docs.feed.radiokot.com.ua/)

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
