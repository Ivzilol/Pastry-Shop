import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";

const ChatRoom = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";
    const [messages, setMessages] = useState({});
    const [newMessage, setNewMessage] = useState("")
    const navigate = useNavigate();

    // eslint-disable-next-line react-hooks/exhaustive-deps
    // function getMessage() {
    //     if (user.jwt) {
    //         ajax(`${baseUrl}api/chatroom`, "GET", user.jwt)
    //             .then(result => {
    //                 // console.log(result)
    //                 setMessages(result)
    //             })
    //     }
    // }

    useEffect(() => {
        if (user.jwt) {
            ajax(`${baseUrl}api/chatroom`, "GET", user.jwt)
                .then(result => {
                    console.log(result)
                    setMessages(result)
                    console.log(messages)
                });
        }
    }, [user.jwt]);

    function sentMessage() {
        if (user.jwt) {
            const requestBody = {
                newMessage: newMessage
            }
            ajax(`${baseUrl}api/chatroom/send`, "POST", user.jwt, requestBody)
                .then((response) => {
                    if (response !== undefined) {
                        return null;
                    } else {
                        return alert("")
                    }
                })
        } else {
            navigate("/login")
        }
    }


    return (
        <main className="chat">
            <section className="chat-container">
                <h1>Chat</h1>
                {messages ? (
                    <div className="chat-container-messages">
                        {messages.map((message) => (
                            <div key={message.id}>
                                <p>{message.message}</p>
                            </div>
                        ))}
                    </div>
                ) : (
                    <></>
                )}
                <div className="chat-container-input">
                    <input
                        type="text"
                        name="message"
                        placeholder="Напиешете съобщение"
                        value={newMessage}
                        onChange={(e) => setNewMessage(e.target.value)}
                    />
                    <button
                        id="submit"
                        type="button"
                        onClick={() => sentMessage()}
                    >
                        Send
                    </button>
                </div>
            </section>
        </main>
    )
}

export default ChatRoom;