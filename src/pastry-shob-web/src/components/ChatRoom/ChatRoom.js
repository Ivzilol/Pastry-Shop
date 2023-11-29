import {useUser} from "../../UserProvider/UserProvider";
import SockJS from 'sockjs-client';
import {over} from 'stompjs';
import baseURL from "../BaseURL/BaseURL";
import {useEffect, useRef, useState} from "react";
import jwt_decode from "jwt-decode";
import ajax from "../../Services/FetchService";
import {useTranslation} from "react-i18next";

let stompClient = null;


const ChatRoom = () => {

    const user = useUser();
    const [roles, setRoles] = useState(null);
    const [publicChats, setPublicChats] = useState([]);
    const [oldMessages, setOldMessages] = useState(null);
    const [chatVisible, setChatVisible] = useState(false);
    const {t} = useTranslation();
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
        messagesEndRef.current?.scrollIntoView({behavior: "smooth"})
    }


    useEffect(() => {
        if (roles !== null) {
            connect();
            getOldMessage();
        }
    }, [roles])


    const adminElement = {
        backgroundColor: 'orange',
        borderRadius: '10px',
        paddingLeft: '10px'
    }

    const sendWithEnter = (e) => {
        if (e.keyCode === 13) {
            sendValue()
        }
    }


    return (
        <main>
            {chatVisible ? (
                <section className="chat" onMouseEnter={scrollToBottom}>
                    <button className="search-result-close-button-chat"
                            type="button"
                            onClick={() => setChatVisible(false)}
                    >{t('search-result-close-button-chat')}
                    </button>
                    <div className="chat-container">
                        {userData.connected ?
                            <div className="chat-box">
                                <div className="chat-content">
                                    <div className="chat-message">
                                        {oldMessages.length > 0
                                            ?
                                            oldMessages.map((oldMessage) => (
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
                                                            <div key={oldMessage.id} style={adminElement}
                                                            >{oldMessage.username}: {oldMessage.message}</div>
                                                            :
                                                            <div key={oldMessage.id} style={adminElement}
                                                            >АДМИН: {oldMessage.message}</div>
                                                        }
                                                    </div>
                                                </div>
                                            )) : (
                                                <div className="empty-chat">{t('empty-chat')}</div>
                                            )}
                                        {publicChats.map((chat, index) => (
                                            chat.message !== null
                                                ?
                                                <div key={index}
                                                     className="chat-message-row">
                                                    <div className="chat-message-data"
                                                         key={index}
                                                         style={adminElement}
                                                    >
                                                        {chat.senderName}: {chat.message}
                                                    </div>
                                                </div>
                                                :
                                                <div className="empty-message"></div>
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
                            onKeyDown={(e) => sendWithEnter(e)}
                        />
                        <button
                            type="button"
                            className="send-button"
                            onClick={sendValue}
                        >
                            send
                        </button>
                    </div>
                </section>
            ) : (
                <button className="chat-closed"
                        onClick={() => setChatVisible(true)}
                >
                    <div>{t('chat-closed')}</div>
                </button>
            )}
        </main>
    )
}

export default ChatRoom;