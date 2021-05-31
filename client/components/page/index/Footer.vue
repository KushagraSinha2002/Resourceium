<template>
  <div
    class="flex justify-between flex-none px-5 my-10 md:justify-around font-styled-code"
  >
    <div>
      <base-animated-number
        class="md:text-right"
        :value="accountsCount"
      ></base-animated-number>
      <div class="text-sm sm:text-base md:text-lg text-light-black">
        People trust us
      </div>
    </div>
    <div>
      <base-animated-number
        class="md:text-right"
        :value="filesCount"
      ></base-animated-number>
      <div class="text-sm sm:text-base md:text-lg text-light-black">
        Files stored safely
      </div>
    </div>
    <div>
      <base-animated-number
        class="md:text-right"
        :value="foldersCount"
      ></base-animated-number>
      <div class="text-sm sm:text-base md:text-lg text-light-black">
        Safes Created
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      foldersCount: 0,
      accountsCount: 0,
      filesCount: 0,
    }
  },
  async fetch() {
    const result = await this.$axios.$get('/core/stats')
    this.foldersCount = result.foldersCount
    this.filesCount = result.filesCount
    this.accountsCount = result.accountsCount
  },
}
</script>
