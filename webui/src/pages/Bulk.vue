<template>
  <div>
    <div class="row">
      <form class="col-lg-12">
        <div class="form-group">
          <label>Phone Number List</label>
          <input v-model.trim="pojoSMS.numbers" class="form-control" type="tel"/>
        </div>
        <div class="form-group">
          <label>Message Contente</label>
          <textarea v-model="pojoSMS.content" class="form-control"></textarea>
        </div>
        <button v-on:click="send" class="btn btn-info">Send SMS</button>
      </form>
    </div>
    <div class="row">
      <table class="table">
        <thead>
        <tr>
          <th>Message Id</th>
          <th>Message</th>
          <th>From</th>
          <th>To</th>
          <th>Coast</th>
          <th>Status</th>
          <th>Created</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="sms in smsOutboxes" v-bind:key="sms.id">
          <td v-text="sms.id"></td>
          <td v-text="sms.message"></td>
          <td v-text="sms.from"></td>
          <td v-text="sms.to"></td>
          <td v-text="sms.coast"></td>
          <td v-text="sms.status"></td>
          <td v-text="parseCreatedSms(sms.created)"></td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
    import {PojoSms} from '../models/pojosms';
    import {loadSmsOutboxes, sendSms} from "../services/sms";

    export default {
        name: "Bulk",
        data() {
            return {
                smsOutboxes: [],
                pojoSMS: new PojoSms()
            }
        }, methods: {
            send: function () {
                sendSms(this.pojoSMS)
                    .then((resp) => {
                        if (resp.status === 200) {
                            window.console.info(resp);
                            this.smsOutboxes = resp.data;
                        }
                    }).catch((error) => {
                    window.console.error(error);
                })
            },
            parseCreatedSms: function (created) {
                return new Date(created).toLocaleDateString();
            }
        }, beforeMount: function () {
            loadSmsOutboxes()
                .then((resp) => {
                    if (resp.status === 200) {
                        window.console.info(resp);
                        this.smsOutboxes = resp.data;
                    }
                }).catch((error) => {
                window.console.error(error);
            })
        }
    }
</script>