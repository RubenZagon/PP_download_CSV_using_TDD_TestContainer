-include .env
export

# Get the absolute path to the running Makefile
ROOT_DIR := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))

# Colours
BLUE:=			\033[0;34m
RED:=			\033[0;31m
LIGHT_RED:=		\033[1;31m
WHITE:=			\033[1;37m
LIGHT_VIOLET := \033[1;35m
NO_COLOUR := 	\033[0m

# Environment : { dev, staging, prod }
ENV := dev

PROJECT_NAME := pet_finances
PROJECT_PORT := 11968

DOCKER_IMAGE_NAME := pet_finances

MSG_SEPARATOR := "*********************************************************************"
MSG_IDENT := "    "


.SILENT:

help:
	echo "\n${MSG_SEPARATOR}\n$(LIGHT_VIOLET)$(PROJECT_NAME)$(NO_COLOUR)\n${MSG_SEPARATOR}\n"
	echo "${MSG_IDENT}=======   ✨  BASIC   =====================================\n   "
	echo "${MSG_IDENT}  ⚠️   Requirements : Java 11 \n"
	echo "${MSG_IDENT}  clean                   -  🚮  Erase the 📁 build/"
	echo "${MSG_IDENT}  build                   -  📦  Build the .jar (ignoring tests)"
	echo "${MSG_IDENT}  test                    -  ✅  Run Unit tests only"
	echo "${MSG_IDENT}  itest                   -  ✅  Run Integration tests only"
	echo "${MSG_IDENT}  run                     -  🚀  Run the app (standalone .jar) with profile '${ENV}'"
	echo "${MSG_IDENT}                                   💡️ To change profile add ENV=[ dev, staging, prod ]\n"
	echo "${MSG_IDENT}    ️                                  > make run ENV=staging"
	echo
	echo "${MSG_IDENT}=======   🐳  DOCKER   =====================================\n"
	echo "${MSG_IDENT}  ℹ️   To work with $(PROJECT_NAME) running alone in a container"
	echo "${MSG_IDENT}  ⚠️   Requirements : docker \n"
	echo "${MSG_IDENT}  dk-build                -  📦  Build a docker image with the .jar"
	echo "${MSG_IDENT}  up                      -  🚀  Start container ${DOCKER_IMAGE_NAME}"
	echo "${MSG_IDENT}  down                    -  🛑  Stop container ${DOCKER_IMAGE_NAME}"
	echo "${MSG_IDENT}  restart                 -  ♻️  Rebuild the application and launch app"
	echo "${MSG_IDENT}  dk-logs                 -  📃️  See logs from the running container ${DOCKER_IMAGE_NAME}"
	echo "${MSG_IDENT}  dk-shell                -  💻️  Shell in the running container ${DOCKER_IMAGE_NAME}"
	echo "${MSG_IDENT}  dk-rmi                  -  🧹  Removing image with name ${DOCKER_IMAGE_NAME}"
	echo

######################################################################
########################   BASIC    #################################
######################################################################

clean:
	echo "\n\n${MSG_SEPARATOR}\n\n CLEAN => 🚮  Erase the 📁 build/\n\n${MSG_SEPARATOR}\n\n"

	./gradlew clean

test: clean
	./gradlew test

itest: clean
	./gradlew integrationTest -x test

build: clean
	./gradlew bootJar -Pversion=$(VERSION)

run: build
	echo "\n\n${MSG_SEPARATOR}\n\n RUN => 🚀 Starting your ☕ app -> http://0.0.0.0:${PROJECT_PORT}/\n\n${MSG_SEPARATOR}\n\n"

	./gradlew bootRun -Dfile.encoding="UTF-8" -Dspring.profiles.active=dev

######################################################################
########################   🐳 DOCKER    ##############################
######################################################################

dk-build: dk-rmi test itest build
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-build => Building the docker image with name ${DOCKER_IMAGE_NAME} ...\n\n${MSG_SEPARATOR}\n\n"

	docker-compose -f docker/docker-compose.yml -f docker/docker-compose.dev.yml build  --force-rm

dk-build-SkipTest: dk-rmi
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-build - ${RED}skip tests${NO_COLOUR} => Building the docker image with name ${DOCKER_IMAGE_NAME} ...\n\n${MSG_SEPARATOR}\n\n"

	./gradlew bootJar -x test -x integrationTest
	docker-compose -f docker/docker-compose.yml -f docker/docker-compose.dev.yml build  --force-rm

dk-build-version:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-build - ${GREEN}versioned with $(IMAGE_VERSION) and profile $(PROFILE)${NO_COLOUR} => Building the docker image with name ${DOCKER_IMAGE_NAME} ...\n\n${MSG_SEPARATOR}\n\n"

	IMAGE_VERSION=$(IMAGE_VERSION)
	PROFILE=$(PROFILE)
	docker-compose -f docker/docker-compose.yml -f docker/docker-compose.override.yml build  --force-rm

up: env-variables
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 up => 🚀  Start container ${DOCKER_IMAGE_NAME} \n\n${MSG_SEPARATOR}\n\n"

	docker-compose -f docker/docker-compose.yml -f docker/docker-compose.dev.yml up

	echo "\n\n${MSG_SEPARATOR}\n\n  🐳 Your app is running 🚀\n"
	echo ""
	echo "  - ⭐️ Application: ${PROJECT_NAME} -> Port: ${PROJECT_PORT}"
	echo "  - 🔍 Go to - http://0.0.0.0:${PROJECT_PORT}/health"
	echo "  - 📑 Documentation - http://0.0.0.0:${PROJECT_PORT}/swagger-ui.html"
	echo "\n${MSG_SEPARATOR}\n\n"

down:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 down => 🚀  Stop container ${DOCKER_IMAGE_NAME} \n\n${MSG_SEPARATOR}\n\n"

	-docker-compose -f docker/docker-compose.yml -f docker/docker-compose.dev.yml down --remove-orphans

restart: env-variables down dk-build-SkipTest up

dk-logs:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-logs => 📃️  See logs from the running container ${DOCKER_IMAGE_NAME} \n\n${MSG_SEPARATOR}\n\n"

	-docker-compose -f docker/docker-compose.yml -f docker/docker-compose.dev.yml logs


dk-shell:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-logs => 💻️  Shell in the running container ${DOCKER_IMAGE_NAME} \n\n${MSG_SEPARATOR}\n\n"

	docker-compose -f docker/docker-compose.yml exec ${PROJECT_NAME} sh


dk-rmi:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-rmi => 🧹  Removing the image ${DOCKER_IMAGE_NAME}\n\n${MSG_SEPARATOR}\n\n"

	-docker rmi ${DOCKER_IMAGE_NAME}

dk-push:
	echo "\n\n${MSG_SEPARATOR}\n\n 🐳 dk-push - ${GREEN}versioned with $(IMAGE_VERSION) and profile $(PROFILE)${NO_COLOUR} => Pushing the docker image with name ${DOCKER_IMAGE_NAME} ...\n\n${MSG_SEPARATOR}\n\n"

	IMAGE_VERSION=$(IMAGE_VERSION)
	PROFILE=$(PROFILE)
	docker-compose -f docker/docker-compose.yml -f docker/docker-compose.override.yml push

######################################################################
###########################   OTHERS    ##############################
######################################################################

env-variables:
	if [ -f .env ]; then \
		true ; \
    else \
	    cp .env-sample .env ; \
		echo "${LIGHT_RED}ERROR - File .env not found:${NO_COLOUR} Generated the file .env, please ${LIGHT_VIOLET}relaunch the last command${NO_COLOUR}"; \
		false ; \
	fi
