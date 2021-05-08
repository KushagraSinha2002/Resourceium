<template>
  <div class="bg-cream-white">
    <div
      class="container flex flex-wrap-reverse items-center h-full pt-16 mx-auto md:pt-0 place-content-center sm:place-content-evenly"
    >
      <div class="w-full px-6 sm:w-1/2 sm:px-0">
        <form
          class="flex flex-col font-poppins max-w-sm sm:max-w-[25rem] md:max-w-md mx-auto md:text-xl sm:text-lg space-y-7 sm:space-y-16"
          @submit.prevent="submitForm"
        >
          <div class="flex flex-col w-full space-y-3 sm:space-y-5">
            <div
              class="mt-10 mb-5 text-3xl font-bold sm:text-4xl sm:mb-10 font-poppins text-dark-black"
            >
              Please Sign In.
            </div>
            <div class="flex flex-col space-y-5 sm:space-y-7">
              <base-input-box
                v-model="formData.username"
                name="username"
                placeholder-text="Username"
                focus-on-render
              ></base-input-box>
              <div>
                <base-input-box
                  v-model="formData.password"
                  name="password"
                  placeholder-text="Password"
                  input-type="password"
                ></base-input-box>
                <div class="mt-3 text-sm ml-2 sm:ml-[15px]">
                  Your Resourceium ID is the username you use to sign in.
                </div>
              </div>
            </div>
          </div>
          <div class="flex flex-col space-y-5">
            <base-loading-button
              type="submit"
              text="Login"
              :loading="loading"
            ></base-loading-button>
            <div class="text-sm sm:text-base text-primary">
              <div>
                <NuxtLink :to="{ name: 'index' }" class="hover:underline">
                  Forgot your Resourceium username or password?
                </NuxtLink>
              </div>
              <div>
                <NuxtLink
                  :to="{ name: 'accounts-register' }"
                  class="hover:underline"
                >
                  Don't have a Resourceium account? Create one here.
                </NuxtLink>
              </div>
            </div>
          </div>
        </form>
      </div>
      <div class="w-full sm:w-1/2">
        <img
          :src="require('~/assets/images/logo.png')"
          alt="Resourceium logo"
          class="h-48 mx-auto sm:h-80 md:h-96 xl:h-[28rem]"
          rel="preload"
        />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      formData: {
        username: '',
        password: '',
      },
    }
  },
  head: () => ({ title: 'Login' }),
  methods: {
    redirectHome() {
      this.$router.push({ name: 'index' })
    },
    async submitForm() {
      const formData = this.formData
      this.loading = true
      await this.$auth
        .loginWith('local', { data: formData })
        .then(() => {
          this.$addAlert({
            message: 'Logged in successfully',
            type: 'success',
          })
        })
        .catch(() => {
          this.$addAlert({
            message: 'Invalid credentials',
            type: 'danger',
          })
        })
      this.loading = false
    },
  },
}
</script>
