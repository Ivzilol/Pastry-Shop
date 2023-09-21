import {useUser} from "../../UserProvider/UserProvider";

const ChatRoom = () => {

    const user = useUser();


    return (
        <main className="chat-container">
            {user.jwt
                ?
                <select>

                </select>
                :
                <></>
            }
        </main>
    )
}

export default ChatRoom;