class PojoSms {
    constructor() {
        this.message = '';
        this.number = '';
    }

    set content(message) {
        this.message = message;
    }

    get content() {
        return this._message;
    }

    get numbers() {
        return this.number;
    }

    set numbers(value) {
        this.number = value;
    }
}


export {PojoSms};