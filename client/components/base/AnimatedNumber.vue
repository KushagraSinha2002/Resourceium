<template>
  <div class="text-2xl font-semibold font-styled text-warm-gray-800">
    {{ displayValue }}+
  </div>
</template>

<script>
import gsap from 'gsap'

export default {
  props: {
    value: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      displayValue: this.value,
      tweenValue: this.value,
    }
  },
  watch: {
    value() {
      gsap.to(this, {
        tweenValue: this.value,
        duration: 5,
        ease: 'power2',
        onUpdate: () => {
          this.displayValue = Math.ceil(this.tweenValue)
        },
      })
    },
  },
}
</script>
