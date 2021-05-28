<template>
  <div class="text-2xl font-semibold font-styled text-warm-gray-800">
    <span v-if="!loaded">0</span>
    <client-only v-else>
      <number
        :from="0"
        :to="value"
        :duration="2.5"
        :delay="1"
        easing="Power1.easeOut"
      ></number>
    </client-only>
    +
  </div>
</template>

<script>
export default {
  props: {
    value: { type: Number, required: true },
  },
  data() {
    return {
      loaded: false,
    }
  },
  mounted() {
    this.loaded = true
  },
}
</script>
