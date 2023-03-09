SRC_FILES := $(wildcard chat/*)

build: $(SRC_FILES)
	javac -d build chat/*.java
	cp chat/Chat.policy build/chat/

Chat.jar: build
	(cd build; jar cef chat.ChatGUI ../Chat.jar *)

run: Chat.jar
	java -jar Chat.jar

run_cmdline: Chat.jar
	java -jar Chat.jar -c

clean:
	rm -rf build
	rm -f Chat.jar
