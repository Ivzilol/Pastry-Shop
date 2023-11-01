import {useUser} from "../../UserProvider/UserProvider";
import SockJS from 'sockjs-client';
import {over} from 'stompjs';
import baseURL from "../BaseURL/BaseURL";
import {useEffect, useRef, useState} from "react";
import jwt_decode from "jwt-decode";
import ajax from "../../Services/FetchService";

let stompClient = null;


const ChatRoom = () => {

    const user = useUser();
    const [roles, setRoles] = useState(null);
    const [publicChats, setPublicChats] = useState([]);
    const [oldMessages, setOldMessages] = useState(null);
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
        stompClient.subscribe(`/chat-rooms/${roles}`, onMessageReceived);
        userJoin();
    }

    const userJoin = () => {
        const chatMessage = {
            senderName: roles,
            status: "JOIN"
        }
        stompClient.send(`/api/message/${roles}`, {}, JSON.stringify(chatMessage));
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
            stompClient.send(`/api/message/${roles}`, {}, JSON.stringify(chatMessage));
            setUserData({...userData, "message": ""})
        }
    }

    function getOldMessage() {
        ajax(`${baseURL}api/chat-room/get-messages/${roles}`, "GET", user.jwt)
            .then((response) => {
                setOldMessages(response);
            })
    }

    const messagesEndRef = useRef(null);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" })
    }


    useEffect(() => {
        if (roles !== null) {
            connect();
            getOldMessage();
        }
    }, [roles])

    const userElement = {
        backgroundColor: 'aqua'
    }

    const adminElement = {
        backgroundColor: 'orange'
    }


    return (
        <main className="chat" onMouseEnter={scrollToBottom}>
            <div className="chat-container">
                {userData.connected ?
                    <div className="chat-box">
                        <div className="chat-content">
                            <div className="chat-message">
                                {oldMessages !== null ? oldMessages.map((oldMessage, index) => (
                                    <div key={oldMessage.id}
                                         className="chat-message-row"
                                         ref={messagesEndRef}
                                    >
                                        <div
                                            className="chat-message-data"
                                            key={oldMessage.id}
                                        >
                                            {oldMessage.adminId === null
                                                ?
                                                <div key={oldMessage.id} style={userElement}
                                                >{oldMessage.username}: {oldMessage.message}</div>
                                                :
                                                <div key={oldMessage.id} style={adminElement}
                                                >Админ: {oldMessage.message}</div>
                                            }
                                        </div>
                                    </div>
                                )) : (
                                    <div className="empty-message"></div>
                                )}
                                {publicChats.map((chat, index) => (
                                    chat.message !== null
                                        ?
                                        <div key={index}
                                             className="chat-message-row"
                                        >
                                            {<div className="chat-message-data"
                                                  key={index}
                                            >{chat.senderName}: {chat.message}</div>
                                            }
                                        </div>
                                        :
                                        <></>
                                ))}
                            </div>
                        </div>
                    </div>
                    :
                    <></>
                }
            </div>
            <div className="send-message">
                <input
                    type="text"
                    className="input-message"
                    placeholder="Send Message"
                    value={userData.message}
                    onChange={handleMessage}
                    onFocus={scrollToBottom}
                />
                <button
                    type="button"
                    className="send-button"
                    onClick={sendValue}
                >
                    send
                </button>
            </div>
        </main>
    )
}

export default ChatRoom;