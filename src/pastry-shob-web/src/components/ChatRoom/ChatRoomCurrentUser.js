import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import jwt_decode from "jwt-decode";
import SockJS from "sockjs-client";
import baseURL from "../BaseURL/BaseURL";
import {over} from "stompjs";

let stompClient = null;

const ChatRoomCurrentUser = () => {


    const user = useUser();
    const navigate = useNavigate();
    const roomCode = window.location.href.split("/chat-room/")[1];
    const [roles, setRoles] = useState(null);
    const [publicChats, setPublicChats] = useState([]);
    const [userData, setUserData] = useState({
        username: '',
        receiverName: '',
        connected: false,
        message: ''
    });

    useEffect(() => {
        const decodeJwt = jwt_decode(user.jwt);
        setRoles(decodeJwt.sub)
    }, [user.jwt])

    function connect() {
        let Sock = new SockJS(`${baseURL}ws`);
        stompClient = over(Sock)
        stompClient.connect({}, onConnected, onError)
    }

    function onConnected() {
        console.log('Свързване успешно установено.')
        setUserData({...userData, "connected": true})
        stompClient.subscribe(`/chat-rooms/${roomCode}`, onMessageReceived);
        userJoin();
    }

    const userJoin = () => {
        const chatMessage = {
            senderName: roles,
            status: "JOIN"
        }
        stompClient.send(`/api/message/${roomCode}`, {}, JSON.stringify(chatMessage));
    }

    const onError = (err) => {
        console.log(err);
    }

    function onMessageReceived(payload) {
        const payloadData = JSON.parse(payload.body);
        publicChats.push(payloadData)
        setPublicChats([...publicChats]);
    }

    const handleMessage = (event) => {
        const {value} = event.target;
        setUserData({...userData, "message": value});
    }

    const sendValue = () => {
        if (stompClient) {
            const chatMessage = {
                senderName: roles,
                message: userData.message,
                status: "MESSAGE"
            }
            if (userData.message.trim() === "") {
                return;
            }
            console.log(chatMessage);
            stompClient.send(`/api/message/${roomCode}`, {}, JSON.stringify(chatMessage));
            setUserData({...userData, "message": ""})
        }
    }

    useEffect(() => {
        if (roles !== null) {
            connect();
        }
    }, [roles])



    return(
        <main><div className="chat-container">
            {userData.connected ?
                <div className="chat-box">
                    <div className="chat-content">
                        <ul className="chat-message">
                            {/*{oldMessages !== null ? oldMessages.map((oldMessage) => (*/}
                            {/*    <li key={oldMessage.id}*/}
                            {/*        className="chat-message-row">*/}
                            {/*        <div*/}
                            {/*            className="chat-message-data">{oldMessage.username}: {oldMessage.message}</div>*/}
                            {/*    </li>*/}
                            {/*)) : (*/}
                            {/*    <></>*/}
                            {/*)}*/}
                            {publicChats.map((chat, index) => (
                                <li key={index}
                                    className="chat-message-row">
                                    {chat.message !== null
                                        ?
                                        <div className="chat-message-data">{chat.senderName}: {chat.message}</div>
                                        :
                                        <></>
                                    }
                                </li>
                            ))}
                        </ul>
                        <div className="send-message">
                            <label>Send message</label>
                            <input
                                type="text"
                                className="input-message"
                                placeholder="Send Message"
                                value={userData.message}
                                onChange={handleMessage}
                            />
                            <button
                                type="button"
                                className="send-button"
                                onClick={sendValue}
                            >
                                send
                            </button>
                        </div>
                    </div>
                </div>
                :
                <></>
            }
        </div></main>
    )
}

export default ChatRoomCurrentUser;