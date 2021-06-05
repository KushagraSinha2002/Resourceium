<template>
  <div class="relative px-5 py-1 border-2 border-gray-500 rounded-md shadow-lg">
    <img
      :src="require(`~/assets/images/${icon}`)"
      class="absolute h-10 sm:h-14 left-2 -top-4 bg-cream-white sm:-top-6"
      :alt="`image for ${label}`"
    />
    <div
      class="text-right text-blue-900 font-poppins font-extralight sm:text-lg"
    >
      {{ label }}
    </div>
    <div class="text-lg text-right sm:text-xl font-poppins">
      {{ value }}/{{ limit }}
    </div>
  </div>
</template>

<script>
export default {
  props: {
    icon: { type: String, required: true },
    label: { type: String, required: true },
    value: { type: [Number, String], required: true },
    limit: { type: [Number, String], required: true },
  },
}
</script>
