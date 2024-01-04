function add_command(name)
    command = java.new(java.import('cross.simplyhomes.test.io.LuaCommand', 'Execute'))

    callback = {}
    function callback:run(sender, handler, args)
        sys:println("command was run")
        return true
    end
    commandInterface = java.proxy('cross.simplyhomes.utils.SimpleCommand', command)
    command:addRunnable(commandInterface)
    command_registry:register(command, name)
    sys:println("went here")
end

sys:println("Plugin initialized")
add_command("discord")
