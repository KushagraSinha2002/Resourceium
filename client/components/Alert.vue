<template>
  <div
    class="absolute inset-0 z-30 flex items-center justify-center bg-white bg-opacity-30"
  >
    <div
      class="relative flex items-center p-3 space-x-5 bg-[#ADA8A9] rounded-b-md w-72 md:w-auto md:max-w-[60%]"
    >
      <div
        :style="{ width: `${width}%` }"
        class="absolute inset-x-0 top-0 h-1 bg-black"
      ></div>
      <ig-icon
        :name="getIconName"
        class="h-10"
        no-size
        variant="black"
      ></ig-icon>
      <div class="text-xs font-semibold md:text-lg font-poppins">
        <div v-if="typeof message == 'string'">
          <div>{{ message }}</div>
        </div>
        <div v-else class="flex flex-col space-y-2">
          <div v-for="(msg, index) in message" :key="index">
            {{ msg }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { sleep } from '../utils/sleep.js'

export default {
  data() {
    return {
      width: 0,
      timer: null,
    }
  },
  computed: {
    ...mapState({
      message: (state) => state.alerts.message,
      timeOut: (state) => state.alerts.timeOut,
      type: (state) => state.alerts.type,
    }),
    getIconName() {
      if (this.type === 'info') return 'info'
      if (this.type === 'danger') return 'alert-triangle'
      if (this.type === 'success') return 'check'
      return 'alert-triangle'
    },
  },
  async mounted() {
    this.timer = window.setTimeout(() => {
      this.disableAlertAction()
    }, this.timeOut)

    for (let i = 0; i <= 100; i++) {
      this.width++
      await sleep(this.timeOut / 100)
    }
  },
  beforeDestroy() {
    window.clearInterval(this.timer)
  },
  methods: {
    ...mapActions({
      disableAlertAction: 'alerts/disableAlert',
    }),
  },
}
</script>
