#info #glfw
_________
## Event Polling
`glfwPollEvents();` - Processes events already received and returns (good for games)
`glfwWaitEvents();` - Thread sleeps until inputs are received (good for modeling programs)
`glfwWaitEventsTimeout(double seconds);` - Thread sleeps until inputs are received or the amount of time has passed
_________
## Keyboard
