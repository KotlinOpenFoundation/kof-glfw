FROM ubuntu

ENV DISPLAY=":99"
ENV GALLIUM_DRIVER="llvmpipe"
ENV LIBGL_ALWAYS_SOFTWARE="1"
ENV MESA_GL_VERSION_OVERRIDE="4.5"
ENV MESA_GLSL_VERSION_OVERRIDE="450"

RUN apt update
RUN apt install -y wget apt-transport-https gpg
RUN wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | tee /etc/apt/trusted.gpg.d/adoptium.gpg > /dev/null
RUN echo "deb [arch=amd64] https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list
RUN apt update
RUN apt install -y temurin-25-jdk
RUN apt install -y build-essential gcc-multilib cmake git wget
RUN apt install -y gcc-aarch64-linux-gnu g++-aarch64-linux-gnu
RUN apt install -y libwayland-dev libxkbcommon-dev xorg-dev libgl-dev xvfb mesa-utils

VOLUME /root/.gradle/
VOLUME /root/.konan/
VOLUME /app/.gradle/

COPY dockerd-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/dockerd-entrypoint.sh

# Pin all existing amd64 sources, handling both formats and any .list files
RUN \
    # Handle classic format in sources.list \
    sed -i 's/^deb \[arch=/deb [arch=amd64,/g' /etc/apt/sources.list && \
    sed -i 's/^deb http/deb [arch=amd64] http/g' /etc/apt/sources.list && \
    # Handle classic format in any .list files \
    find /etc/apt/sources.list.d/ -name "*.list" -exec \
        sed -i 's/^deb \[arch=/deb [arch=amd64,/g' {} + && \
    find /etc/apt/sources.list.d/ -name "*.list" -exec \
        sed -i 's/^deb http/deb [arch=amd64] http/g' {} + && \
    # Handle DEB822 format (.sources files) - add Architectures if not present \
    find /etc/apt/sources.list.d/ -name "*.sources" -exec \
        sed -i '/^Architectures:/d' {} + && \
    find /etc/apt/sources.list.d/ -name "*.sources" -exec \
        sed -i 's/^Types: deb/Types: deb\nArchitectures: amd64/' {} +

# Add arm64 ports
RUN tee /etc/apt/sources.list.d/arm64-ports.list > /dev/null <<EOF
deb [arch=arm64] https://ports.ubuntu.com/ubuntu-ports noble main restricted universe multiverse
deb [arch=arm64] https://ports.ubuntu.com/ubuntu-ports noble-updates main restricted universe multiverse
deb [arch=arm64] https://ports.ubuntu.com/ubuntu-ports noble-security main restricted universe multiverse
deb [arch=arm64] https://ports.ubuntu.com/ubuntu-ports noble-backports main restricted universe multiverse
EOF

# Add arm64 architecture and install
RUN dpkg --add-architecture arm64
RUN apt update && apt install -y libx11-dev:arm64

WORKDIR /app

ENTRYPOINT ["dockerd-entrypoint.sh"]
