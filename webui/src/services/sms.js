import axios from "axios";


async function sendSms(pojoSMS) {
    let uri = 'http://localhost:9090/api/sms/bulk/send';
    return await axios.post(uri, pojoSMS);
}

async function loadSmsOutboxes() {
    let uri = 'http://localhost:9090/api/sms/outboxes';
    return await axios.get(uri);
}

async function loadSubscriber(shortCode, keyWord) {
    let uri = `http://localhost:9090/api/sms/subscriptions/${shortCode}/${keyWord}`;
    return await axios.get(uri);
}

export {sendSms, loadSmsOutboxes};