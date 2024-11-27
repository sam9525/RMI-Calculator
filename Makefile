# Determine the platform

UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Darwin)
    # macOS
    RM = rm -rf
		OPEN_TERMINAL = open -a Terminal .

else ifeq ($(UNAME_S),Linux)
		# Linux
		RM = rm -rf
		OPEN_TERMINAL = open -a Terminal .
else
    # Windows
    RM = rmdir /s /q
		OPEN_TERMINAL = start cmd
endif

JAVAC = javac
JAVA = java

MAIN = Calculator

BIN_DIR = bin

all: compile

compile: 
		$(JAVAC) -d $(BIN_DIR) src/Interface/$(MAIN).java src/Interface/$(MAIN)Implementation.java src/Server/$(MAIN)Server.java src/Client/$(MAIN)Client.java

runRegistry:
		$(OPEN_TERMINAL) && cd $(BIN_DIR) && rmiregistry

runServer: 
		$(OPEN_TERMINAL) && cd $(BIN_DIR) && $(JAVA) Server.$(MAIN)Server

runClient:
		cd $(BIN_DIR) && $(JAVA) Client.$(MAIN)Client
		

clean: 
		$(RM) $(BIN_DIR)