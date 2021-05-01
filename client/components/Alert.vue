<template>
  <div
    class="absolute inset-0 z-30 flex items-center justify-center bg-white bg-opacity-30"
  >
    <div class="relative flex items-center px-5 py-2 space-x-5 border">
      <div
        :style="{ width: `${width}%` }"
        class="absolute inset-x-0 top-0 h-0.5 bg-red-600"
      ></div>
      <ig-icon
        :name="getIconName"
        size="xl"
        no-color
        :class="getIconColor"
      ></ig-icon>
      <div>{{ message }}</div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

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
      if (this.type === 'danger') return 'x-octagon'
      if (this.type === 'success') return 'check'
      return 'alert-triangle'
    },
    getIconColor() {
      if (this.type === 'info') return 'text-blue-500'
      if (this.type === 'danger') return 'text-red-500'
      if (this.type === 'success') return 'text-lime-green-500'
      return 'text-yellow-600'
    },
  },
  async mounted() {
    this.timer = window.setTimeout(() => {
      this.disableAlertAction()
    }, this.timeOut)

    for (let i = 0; i <= 100; i++) {
      this.width++
      await this.sleep(this.timeOut / 100)
    }
  },
  beforeDestroy() {
    window.clearInterval(this.timer)
  },
  methods: {
    ...mapActions({
      disableAlertAction: 'alerts/disableAlert',
    }),
    sleep(ms) {
      return new Promise((resolve) => setTimeout(resolve, ms))
    },
  },
}
</script>
