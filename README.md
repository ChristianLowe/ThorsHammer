![Logo of Thor's Hammer](thors-hammer-logo.png)

# Thor's Hammer
> Idle RPG for friends

**Thor's Hammer** is a Discord bot which provides a text-based game that you can 
play with your friends on your Discord server. This game adapts elements from
RuneScape and provides them in an Idle RPG format.

## Invite to your server
If you just want to play, you can invite the main **Thor's Hammer** bot to your 
server through [this link](https://discordapp.com/oauth2/authorize?client_id=442496247933173760&scope=bot). 
You will need to make a channel named `#ThorsHammer`. Type `help` in that channel 
to get started. The rest of this document is for those who wish to develop or 
set up their own instance.


## Prerequisites

* [Gradle 4.7](https://gradle.org/install/)
* [Docker](https://docs.docker.com/install/)
* [Docker Compose](https://docs.docker.com/compose/)

## Installing

Before getting started, you will need to [create your bot through Discord](https://discordapp.com/developers/applications/me). 
After getting the credentials for your new bot, rename `example.config.txt` 
to `config.txt` and replace the sample credentials with your own.

Now, building the project and running the bot should be as simple as running 
these commands inside of the project's root directory:

```shell
gradle build docker
docker-compose up
```

If all goes well, that's it! Check the logs for a link to invite the bot to 
your server.

## Contributing

Thank you for your interest. **Thor's Hammer** is written in [Kotlin](https://kotlinlang.org/) 
and utilizes [JDA](https://github.com/DV8FromTheWorld/JDA) to access Discord.

If you'd like to contribute, please fork the repository and use a feature 
branch. Pull requests are warmly welcome. Alternatively, let me know of any 
issues you face by filing a report in the project's 
[issue tracker](https://github.com/ChristianLowe/ThorsHammer/issues).

## Licensing

The code in this project is licensed under [the MIT license](https://tldrlegal.com/license/mit-license).
