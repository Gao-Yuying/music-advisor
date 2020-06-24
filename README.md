# music-advisor
A music web application using [Spotify's APIs.](https://developer.spotify.com/documentation/web-api/reference/browse/)

## Available commands
- `auth`: user must authorize the APP to use it's features. It provides a **URL** to confirm authorization:
```
https://accounts.spotify.com/authorize?client_id=[client_id_of_user]&redirect_uri=[your_server]&response_type=code
```
![authorization page](https://github.com/Gao-Yuying/music-advisor/blob/master/Music%20Advisor/task/src/advisor/res/auth.JPG "authorization page")
- `featured`: displays a list of Spotify-featured playlists with their links fetched from API.
- `new`: displays a list of new albums with artists and links on Spotify.
- `categories`: displays a list of all available categories on Spotify (just their names.)
- `playlists C_NAME`: displays a list of playlists of `C_NAME` (the name of category) and their links on Spotify.
- `prev`: displays previous page of data.
- `next`: displays next page of data.
- `exit`: shuts down the application.

## What you'll see
![demo.gif](https://github.com/Gao-Yuying/music-advisor/blob/master/Music%20Advisor/task/src/advisor/res/demo.gif "demo")
