<template>
  <div class="text-2xl font-semibold font-styled text-warm-gray-800">
    {{ displayNumber }}+
  </div>
</template>

<script>
export default {
  props: {
    number: {
      required: true,
      type: Number,
    },
  },
  data() {
    return {
      displayNumber: 0,
      interval: false,
    }
  },
  watch: {
    number() {
      clearInterval(this.interval)

      if (this.number === this.displayNumber) {
        return
      }

      this.interval = window.setInterval(() => {
        if (this.displayNumber !== this.number) {
          let change = (this.number - this.displayNumber) / 10
          change = change >= 0 ? Math.ceil(change) : Math.floor(change)
          this.displayNumber = this.displayNumber + change
        }
      }, 100)
    },
  },
  mounted() {
    this.displayNumber = this.number ? this.number : 0
  },
}
</script>
