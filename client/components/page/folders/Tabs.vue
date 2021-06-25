<template>
  <div class="flex justify-around w-full text-xl md:text-3xl font-poppins">
    <div
      v-for="(tab, index) in possible"
      :key="index"
      class="px-3 py-1 transition-shadow duration-300 cursor-pointer rounded-15px"
      :class="{ 'ring-2 ring-rose-600/60': value === tab }"
      @click="selectOption(tab)"
    >
      {{ tab }}
    </div>
  </div>
</template>

<script>
export default {
  props: {
    value: { type: String, required: true },
    possible: { type: Array, required: true },
  },
  methods: {
    selectOption(tab) {
      this.$emit('input', tab)
    },
  },
}
</script>
