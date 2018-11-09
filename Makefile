.PHONY: publish
NVM=8.10.0

clean:
	rm -rf build
	rm -rf node_modules

node_modules:
	. "${NVM_DIR}/nvm.sh" && nvm use $(NVM) && yarn install

build: node_modules
	. "${NVM_DIR}/nvm.sh" && nvm use $(NVM) && yarn build

start: build
	. "${NVM_DIR}/nvm.sh" && nvm use $(NVM) && yarn start

test: build
	cd tests && ./gradlew clean test

watch:
	watchman-make -p 'content/**/*.md' --run ./compileContent.sh

test_ie:
	cd tests && ./gradlew --no-daemon --stacktrace -Dselenide.baseUrl='http://192.168.1.2:3000/' -DdriverName=ie -PbuildDir=build_ie clean test

test_edge:
	cd tests && ./gradlew --no-daemon --stacktrace -Dselenide.baseUrl='http://192.168.1.2:3000/' -DdriverName=edge -PbuildDir=build_edge clean test

storybook: build
	. "${NVM_DIR}/nvm.sh" && nvm use $(NVM) && yarn storybook

publish: build
	. "${NVM_DIR}/nvm.sh" && nvm use $(NVM) && yarn run deploy
