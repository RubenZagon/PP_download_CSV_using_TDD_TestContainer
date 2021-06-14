

I did so many changes to improve the code, but if you want to see a basic version of how to implement an endpoint to download a CSV file with TDD, TestContainer, Docker and SpringBoot. Follow the link below 

[Go to Brach - basic version csv downloader](https://github.com/RubenZagon/pet_finances_java/tree/basic_version_download_csv)


---

[![CI - Gradle]

## First setup
Copy the file `.env-sample` and rename the copy to `.env`. Now you need open a new Terminal and in the root of project
use.
```bash
. ./.env
```
To export the environment variables.

## Use make for ease your life
If you have make installed it will call maven and docker for you

Try to see the commands

```bash
make 
```

## Run the project

```bash
make dk-build up
```

A little tips, if you use `make restart` it's the quick step to up the app